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

open class Movie {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("original_title")
    var originalTitle: String? = ""
    @SerializedName("title")
    var title: String? = ""
    @SerializedName("poster_path")
    var posterPath: String? = ""
    @SerializedName("genre_ids")
    var genreIds: List<Int> = ArrayList()
}

data class MakeFavouriteRequest(
    @SerializedName("media_type") var mediaType: String?,
    @SerializedName("media_id") var mediaId: Int?,
    @SerializedName("favorite") var isFavourite: Boolean?
)

data class MakeFavouriteResponse(
    @SerializedName("status_code") var statusCode: Int?,
    @SerializedName("status_message") var statusMessage: String?
)

data class MovieDetailsResponse(
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("original_language") var originalLanguage: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("spoken_languages") var spokenLanguage: List<Language>?,
    @SerializedName("video") var video: Boolean? = false,
    @SerializedName("vote_average") var voteAverage: Double?,
    @SerializedName("vote_count") var voteCount: Double?,
    @SerializedName("homepage") var homePage: String?
) : Movie()

data class Language(
    @SerializedName("iso_639_1") var iso6391: String?,
    @SerializedName("name") var name: String?
)