package com.example.themoviedb.network

import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.Movies
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The interface which provides methods to get result of webservices
 */
interface MovieApi {
    /**
     * Get the list of the popular movie from the API
     */
    @GET("movie/popular")
    fun getPopularMovies(): Observable<Movies>

    //    val url = originalHttpUrl.newBuilder()
//        .addQueryParameter("api_key", apiKey)
//        .build()
    @GET("movie/{id}")
    fun getMovieDetail(@Path("id") id: Int): Call<Movie>
}