package com.yunus.moviedb.repository

import com.yunus.moviedb.data.PopularMovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServiceApi {
    @Headers("Content-Type: application/json")
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                         @Query("page") page: String): Observable<PopularMovieResponse>

}