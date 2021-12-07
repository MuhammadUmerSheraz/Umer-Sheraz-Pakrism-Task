package umer.task.pakrism.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetail(
    @PrimaryKey
    val movieId: Int,
    val backdrop_path: String, val original_title: String, val overview: String,
    val poster_path: String, val tagline: String, val status: String,
    val release_date: String, val vote_average: Double
)