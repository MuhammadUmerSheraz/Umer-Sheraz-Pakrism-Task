package umer.task.pakrism.localSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import umer.task.pakrism.model.db.Movies

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovies(movies : List<Movies>)

    @Query("SELECT * FROM Movies ORDER BY original_title ASC")
    fun getAllUpComingMovies() : List<Movies>

}