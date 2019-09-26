package com.yunus.moviedb.data

import com.google.gson.annotations.SerializedName


open class PopularMovieResponse(
    @SerializedName("results") var movies: List<Movie>
)

open class Movie(
    @SerializedName("original_title") var originalTitle: String?,
    @SerializedName("poster_path") var posterPath: String?
)