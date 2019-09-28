package com.yunus.moviedb.data

import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    @SerializedName("results") var movies: List<Movie>,
    @SerializedName("total_pages") var totalPage: Int
)

data class GenresResponse(
    @SerializedName("genres") var genres: List<Genre>
)

data class Genre(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
)

data class Movie(
    @SerializedName("original_title") var originalTitle: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("genre_ids") var genreIds: List<Int>
)