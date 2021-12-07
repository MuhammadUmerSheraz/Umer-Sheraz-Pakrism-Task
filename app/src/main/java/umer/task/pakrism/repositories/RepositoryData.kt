package umer.task.pakrism.repositories

import android.content.Context
import umer.task.pakrism.localSource.AppDatabase
import umer.task.pakrism.remoteSource.ApiInterface
import umer.task.pakrism.remoteSource.Movie
import umer.task.pakrism.remoteSource.MovieResponseDetails
import umer.task.pakrism.ui.Movies
import umer.task.pakrism.utils.SafeApiRequest
import umer.task.pakrism.utils.UseCaseResult

class RepositoryData(context: Context,    private val apiInterface: ApiInterface
) : SafeApiRequest(context) {



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
            val result = apiInterface.getMovie(umer.task.pakrism.AppConstants.API_KEY).await()
            val list = getMappedMovies(result.results)
            UseCaseResult.Success(list)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }


    }

    fun getMoviesFromLocal(): UseCaseResult<List<Movies>> {
        val list = appDatabase.getMoviesDao().getAllUpComingMovies()
        return UseCaseResult.Success(list)
    }


   suspend fun getMovieDetail(movieId : String) :UseCaseResult<MovieResponseDetails> {
       return try {
            val result =apiInterface.getMovieDetails(movieId, umer.task.pakrism.AppConstants.API_KEY).await()
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