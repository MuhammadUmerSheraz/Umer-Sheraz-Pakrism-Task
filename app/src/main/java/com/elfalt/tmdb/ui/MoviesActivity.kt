package com.elfalt.tmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elfalt.tmdb.R
import com.elfalt.tmdb.adapters.MoviesAdapter
import com.elfalt.tmdb.base.BaseActivty
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        movieRecyclerView.adapter =
            MoviesAdapter(moviesList)

    }

}
