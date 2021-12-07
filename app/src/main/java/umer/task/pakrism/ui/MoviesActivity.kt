package umer.task.pakrism.ui

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_movies.*
import umer.task.pakrism.R
import umer.task.pakrism.base.BaseActivty
import org.koin.androidx.viewmodel.ext.android.viewModel
import umer.task.pakrism.adapters.MoviesAdapter
import umer.task.pakrism.model.Movies

class MoviesActivity : BaseActivty() {

    private val moviesViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        getRecyclerViewData()

    }

    private fun getRecyclerViewData() {
        moviesViewModel.getMovies()
        moviesViewModel.movieList.observe(this, {
            populateMovieRecycler(it)
        })
    }

    private fun populateMovieRecycler(moviesList: List<Movies>) {

        movieRecyclerView.adapter = MoviesAdapter(moviesList) {
            val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java)
            movieDetailsIntent.putExtra("id", it.id.toString())
            startActivity(movieDetailsIntent)
        }

    }

}
