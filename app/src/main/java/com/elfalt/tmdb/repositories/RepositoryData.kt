package com.elfalt.tmdb.repositories

import android.content.Context
import com.elfalt.tmdb.AppConstants
import com.elfalt.tmdb.localSource.AppDatabase
import com.elfalt.tmdb.remoteSource.ApiInterface
import com.elfalt.tmdb.remoteSource.Movie
import com.elfalt.tmdb.remoteSource.MovieResponseDetails
import com.elfalt.tmdb.ui.Movies
import com.elfalt.tmdb.utils.SafeApiRequest
import com.elfalt.tmdb.utils.UseCaseResult
import okhttp3.internal.wait

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
            val result = apiInterface.getMovie(AppConstants.API_KEY).await()
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
            val result =apiInterface.getMovieDetails(movieId,AppConstants.API_KEY).await()
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