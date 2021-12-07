package umer.task.pakrism.repositories

import android.content.Context
import umer.task.pakrism.AppConstants
import umer.task.pakrism.localSource.AppDatabase
import umer.task.pakrism.model.db.MovieDetail
import umer.task.pakrism.remoteSource.ApiInterface
import umer.task.pakrism.model.respone.Movie
import umer.task.pakrism.model.db.Movies
import umer.task.pakrism.utils.UseCaseResult

class RepositoryData(
    context: Context, private val apiInterface: ApiInterface
)  {
    private lateinit var appDatabase: AppDatabase

    init {
        initDatabase(context)
    }

    fun initDatabase(context: Context) {
        if (!this::appDatabase.isInitialized)
            appDatabase = AppDatabase.getDatabase(context)
    }


    suspend fun loadMoviesRemote(): UseCaseResult<List<Movies>> {

        return try {
            val result = apiInterface.getMovie(AppConstants.API_KEY).await()
            var list = getMappedMovies(result.results)
            list=list.sortedBy {
                it.original_title
            }
            appDatabase.getMoviesDao().insertAllMovies(list)
            UseCaseResult.Success(list)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }


    }

    fun getMoviesFromLocal(): UseCaseResult<List<Movies>> {
        val list = appDatabase.getMoviesDao().getAllUpComingMovies()
        return UseCaseResult.Success(list)
    }


    suspend fun getMovieDetail(movieId: String): UseCaseResult<MovieDetail> {
        return try {
            val result = apiInterface.getMovieDetails(movieId, AppConstants.API_KEY).await()
            result.run {

                val movieDetail = MovieDetail(
                    movieId.toInt(), backdrop_path, original_title, overview,
                    poster_path, tagline, status, release_date, vote_average
                )
                appDatabase.getMoviesDetailDao().insertMovieDetail(movieDetail)
                UseCaseResult.Success(movieDetail)

            }

        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }

    }

    suspend fun getMovieDetailLocal(movieId: String): UseCaseResult<MovieDetail> {
        return try {
            val result = appDatabase.getMoviesDetailDao().getMovieDetail(movieId.toInt())
            UseCaseResult.Success(result)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }

    }

    fun getMappedMovies(movies: List<Movie>): List<Movies> {

        val localMovies: MutableList<Movies> = mutableListOf()
        movies.forEach {
            localMovies.add(
                Movies(it.id, it.original_title, it.release_date, it.poster_path, it.vote_average)
            )
        }
        return localMovies
    }

}