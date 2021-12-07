package umer.task.pakrism.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import umer.task.pakrism.R
import umer.task.pakrism.base.BaseActivty
import org.koin.androidx.viewmodel.ext.android.viewModel
import umer.task.pakrism.AppConstants
import umer.task.pakrism.di.Modules.BASE_IMAGE_URL
import umer.task.pakrism.model.db.MovieDetail

class MovieDetailsActivity : BaseActivty() {

    lateinit var movieId: String
    lateinit var movieDetail: MovieDetail
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("movieDetail")
        ) {
            movieDetail = Gson().fromJson(
                intent.getStringExtra("movieDetail"),
                object : TypeToken<MovieDetail>() {}.type
            )
            setData(movieDetail)
        }
        movieId = intent.getStringExtra("id").toString()
        if (movieViewModel.isNetwork()
        ) {
            movieViewModel.getMovieDetails(movieId)
        }


        movieViewModel.movieDetail.observe(this, { movie ->
            setData(movie)

        })


    }

    private fun setData(movie: MovieDetail) {
        Picasso.get().load(BASE_IMAGE_URL + movie.backdrop_path)
            .into(backdrop_path)
        movie_name.text = movie.original_title
        overview_detail.text = movie.overview
        tagLineDetails.text = movie.tagline
        statusDetails.text = movie.status
        releaseDateDetails.text = movie.release_date
        vote_average_detail.text = movie.vote_average.toString()
        buy.setOnClickListener {
            startActivity(Intent(this, BuyMovieActivity::class.java).apply {
                putExtra(AppConstants.name, movie.original_title)
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
