package com.yunus.moviedb.feature.dashboard

import com.yunus.moviedb.R
import com.yunus.moviedb.feature.common.SimpleViewModel

class MovieItemViewModel(var imageUrl: String?, var movieTitle: String?, var movieGenre: String?) : SimpleViewModel{
    override fun layoutId(): Int = R.layout.view_movie_item
}