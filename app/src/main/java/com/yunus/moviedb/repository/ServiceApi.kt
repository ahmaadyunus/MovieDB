package com.yunus.moviedb.repository

import com.yunus.moviedb.data.CreateSessionLoginRequest
import com.yunus.moviedb.data.MoviesResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ServiceApi {
    @Headers("Content-Type: application/json")
    @GET("authentication/token/new")
    fun createAccessToken(@Query("api_key") apiKey: String): Observable<MoviesResponse>

    @Headers("Content-Type: application/json")
    @POST("authentication/token/validate_with_login")
    fun createAccessTokenWithLogin(@Query("api_key") apiKey: String,
                                   @Body request: CreateSessionLoginRequest): Observable<MoviesResponse>

    @Headers("Content-Type: application/json")
    @POST("authentication/session/new")
    fun createSession(@Query("api_key") apiKey: String,
                      @Body request: CreateSessionLoginRequest): Observable<MoviesResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String,
                         @Query("page") page: Int): Observable<MoviesResponse>

    @Headers("Content-Type: application/json")
    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String,
                         @Query("page") page: Int): Observable<MoviesResponse>

    @Headers("Content-Type: application/json")
    @GET("account/150419/favorite/movies")
    fun getFavorited(@Query("api_key") apiKey: String,
                     @Query("session_id") sessionId: String,
                     @Query("sort_by") sortBy: String,
                          @Query("page") page: Int): Observable<MoviesResponse>
}