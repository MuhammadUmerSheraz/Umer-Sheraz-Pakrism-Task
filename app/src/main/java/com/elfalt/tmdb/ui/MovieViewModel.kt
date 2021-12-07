package com.elfalt.tmdb.ui

import com.elfalt.tmdb.base.BaseViewModel
import com.elfalt.tmdb.remoteSource.MovieResponseDetails
import com.elfalt.tmdb.repositories.RepositoryData
import com.elfalt.tmdb.utils.NetworkCheck
import com.elfalt.tmdb.utils.SingleLiveEvent
import com.elfalt.tmdb.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(val networkCheck: NetworkCheck, val repositoryData: RepositoryData) :
    BaseViewModel() {
    val movieList = SingleLiveEvent<List<Movies>>()
    val movieDetail = SingleLiveEvent<MovieResponseDetails>()


    fun getMovies() {
        isLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {

                if (networkCheck.isInternetAvailable()) {
                    repositoryData.loadMoviesRemote()
                } else {
                    repositoryData.getMoviesFromLocal()
                }

            }
            isLoading.value = false
            when (result) {
                is UseCaseResult.Success -> {
                    movieList.value = result.data
                }
                is UseCaseResult.Error -> isError.value = result.exception.message
            }
        }
    }


    fun getMovieDetails(movieId : String) {
        isLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                repositoryData.getMovieDetail(movieId)

            }
            isLoading.value = false
            when (result) {
                is UseCaseResult.Success -> {
                    movieDetail.value = result.data
                }
                is UseCaseResult.Error -> isError.value = result.exception.message
            }
        }
    }

}