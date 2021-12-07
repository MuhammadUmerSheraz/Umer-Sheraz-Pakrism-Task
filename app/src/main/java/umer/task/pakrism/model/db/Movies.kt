package umer.task.pakrism.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Movies (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val original_title :String,
    val release_date :String,
    val poster_path  :String,
    val vote_average : Float
)