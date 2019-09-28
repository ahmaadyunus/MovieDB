package com.yunus.moviedb.feature.dashboard

import com.yunus.moviedb.R
import com.yunus.moviedb.feature.common.SimpleViewModel

class MovieItemViewModel(
    var movieId: Int?,
    var imageUrl: String?,
    var movieTitle: String?,
    var movieGenre: String?,
    var isLiked: Boolean = false,
    var btnLikeDrawable: Int = R.drawable.ic_like_inactive,
    var cbOnlike: (MovieItemViewModel) -> Unit
) : SimpleViewModel {
    override fun layoutId(): Int = R.layout.view_movie_item

    fun onLike() {
        cbOnlike.invoke(this)
        isLiked = !isLiked
        btnLikeDrawable = if (isLiked) {
            R.drawable.ic_like_active
        } else {
            R.drawable.ic_like_inactive
        }
    }
}