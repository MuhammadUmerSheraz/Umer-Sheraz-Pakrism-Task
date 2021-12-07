package umer.task.pakrism.remoteSource

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/3/movie/upcoming")
    fun getMovie(@Query("api_key") api_key : String) : Deferred<MovieResponse>


    @GET("/3/movie/{id}")
    fun getMovieDetails(@Path("id") id : String, @Query ("api_key") api_key: String) : Deferred<MovieResponseDetails>


}