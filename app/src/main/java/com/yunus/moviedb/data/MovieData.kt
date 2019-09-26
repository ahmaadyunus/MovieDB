package com.yunus.moviedb.data

import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    @SerializedName("results") var movies: List<Movie>
)

data class Movie(
    @SerializedName("original_title") var originalTitle: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("poster_path") var posterPath: String?
)