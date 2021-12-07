package umer.task.pakrism.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import umer.task.pakrism.R
import umer.task.pakrism.base.BaseActivty
import org.koin.androidx.viewmodel.ext.android.viewModel
import umer.task.pakrism.di.Modules.BASE_IMAGE_URL

class MovieDetailsActivity : BaseActivty() {

    lateinit var movieId: String
    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)


        movieId = intent.getStringExtra("id").toString()
        movieViewModel.getMovieDetails(movieId)


        movieViewModel.movieDetail.observe(this, {
            Picasso.get().load(BASE_IMAGE_URL + it.backdrop_path)
                .into(backdrop_path)

            Picasso.get().load(BASE_IMAGE_URL + it.poster_path)
                .into(poster_path)

            movie_name.text = it.original_title
            overview_detail.text = it.overview
            tagLineDetails.text = it.tagline
            statusDetails.text = it.status
            releaseDateDetails.text = it.release_date
            vote_average_detail.text = it.vote_average.toString()

            buy.setOnClickListener {
                startActivity(Intent(this, BuyMovieActivity::class.java).apply {
                    putExtra("id", it.id.toString())
                })
            }
        })

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
