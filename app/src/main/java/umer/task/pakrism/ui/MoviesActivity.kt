package umer.task.pakrism.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Filter
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.item_list_movies_content.view.*
import umer.task.pakrism.R
import umer.task.pakrism.base.BaseActivty
import org.koin.androidx.viewmodel.ext.android.viewModel
import umer.task.pakrism.adapters.GenericListAdapter
import umer.task.pakrism.di.Modules
import umer.task.pakrism.model.db.Movies

class MoviesActivity : BaseActivty() {

    private val moviesViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        getSupportActionBar()!!.title = getString(R.string.upcoming_movie)

        getRecyclerViewData()

    }

    private fun getRecyclerViewData() {
        moviesViewModel.getMovies()
        moviesViewModel.movieList.observe(this, {
            no_record_found.isVisible = it.size == 0
            populateMovieRecycler(it)
        })
    }

    private fun populateMovieRecycler(moviesList: List<Movies>) {


        movieRecyclerView.adapter = object : GenericListAdapter<Movies>(
            R.layout.item_list_movies_content,
            bind = { element, holder, itemCount, position ->
                holder.view.run {
                    element.run {
                        movieName.text = original_title
                        val releaseDate: ArrayList<String> =
                            release_date.split('-') as ArrayList<String>
                        movieYear.text = releaseDate[0]
                        rating_bar.rating = vote_average / 2

                        Picasso.get().load(Modules.BASE_IMAGE_URL + poster_path).into(poster)

                        setOnClickListener {
                            if (moviesViewModel.isNetwork()
                            ) {
                                val movieDetailsIntent =
                                    Intent(this@MoviesActivity, MovieDetailsActivity::class.java)
                                movieDetailsIntent.putExtra("id", this.id.toString())
                                startActivity(movieDetailsIntent)
                            } else {

                                moviesViewModel.getMovieDetailLocal(this.id.toString(), {
                                    val movieDetailsIntent =
                                        Intent(
                                            this@MoviesActivity,
                                            MovieDetailsActivity::class.java
                                        )
                                    movieDetailsIntent.putExtra(
                                        "movieDetail",
                                        Gson().toJson(it)
                                    )
                                    startActivity(movieDetailsIntent)
                                }, {
                                    moviesViewModel.isError.value =
                                        getString(R.string.no_internet)
                                })
                            }

                        }
                    }
                }
            }
        ) {
            override fun getFilter(): Filter {
                TODO("Not yet implemented")
            }

        }.apply {
            submitList(moviesList)
        }


    }

}
