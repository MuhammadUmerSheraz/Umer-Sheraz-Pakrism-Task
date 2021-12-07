package umer.task.pakrism.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_movies_content.view.*
import umer.task.pakrism.R
import umer.task.pakrism.di.Modules.BASE_IMAGE_URL
import umer.task.pakrism.model.Movies

class MoviesAdapter(val moviesList : List<Movies>, val onClick: (movie: Movies) -> Unit, ) :
    RecyclerView.Adapter<ViewHolder1>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        val movieView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_movies_content,parent,false)
        return ViewHolder1(movieView)

    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        holder.view.run {
            moviesList[position].run{
                movieName.text = original_title
                val releaseDate: ArrayList<String>  = release_date.split('-') as ArrayList<String>
                movieYear.text = releaseDate[0]
                rating_bar.rating = vote_average / 2

                Picasso.get().load(BASE_IMAGE_URL + poster_path).into(poster)

                setOnClickListener{
                    onClick.invoke(this)
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

}