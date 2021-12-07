package umer.task.pakrism.ui

import umer.task.pakrism.base.BaseViewModel
import umer.task.pakrism.model.respone.MovieResponseDetails
import umer.task.pakrism.repositories.RepositoryData
import umer.task.pakrism.utils.NetworkCheck
import umer.task.pakrism.utils.SingleLiveEvent
import umer.task.pakrism.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import umer.task.pakrism.adapters.BaseViewHolder
import umer.task.pakrism.model.db.MovieDetail
import umer.task.pakrism.model.db.Movies

class MovieViewModel(val networkCheck: NetworkCheck, val repositoryData: RepositoryData) :
    BaseViewModel() {
    val movieList = SingleLiveEvent<List<Movies>>()
    val movieDetail = SingleLiveEvent<MovieDetail>()

    fun isNetwork(): Boolean {
        return networkCheck.isInternetAvailable()
    }

    fun getMovies() {
        isLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {

                if (isNetwork()) {
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


    fun getMovieDetails(movieId: String) {
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

    fun getMovieDetailLocal(
        movieId: String,
        onSuccess: (movieDetail: MovieDetail) -> Unit,
        onFailure: () -> Unit
    ){

        isLoading.value = true
        launch {
            val result = withContext(Dispatchers.IO) {
                repositoryData.getMovieDetailLocal(movieId)
            }
            isLoading.value = false
            when (result) {
                is UseCaseResult.Success -> {
                    onSuccess.invoke(result.data)
                }
                is UseCaseResult.Error -> {
                    onFailure.invoke()
                }
            }
        }
    }
}