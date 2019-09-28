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
    @SerializedName("id") var id: Int?,
    @SerializedName("original_title") var originalTitle: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("genre_ids") var genreIds: List<Int>
)

data class MakeFavouriteRequest(
    @SerializedName("media_type") var mediaType: String?,
    @SerializedName("media_id") var mediaId: Int?,
    @SerializedName("favorite") var isFavourite: Boolean?
)

data class MakeFavouriteResponse(
    @SerializedName("status_code") var statusCode: Int?,
    @SerializedName("status_message") var statusMessage: String?
)