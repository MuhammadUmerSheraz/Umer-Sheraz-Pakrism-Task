package umer.task.pakrism.localSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import umer.task.pakrism.model.db.MovieDetail


@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetail(movieDetail : MovieDetail)

    @Query("SELECT * FROM MovieDetail WHERE movieId =:movieId")
    fun getMovieDetail(movieId:Int) : MovieDetail

}