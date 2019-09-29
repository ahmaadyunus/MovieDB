package com.yunus.moviedb.feature.moviedetails

import android.os.Bundle
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseActivity
import com.yunus.moviedb.base.Constants.IS_LIKED
import com.yunus.moviedb.base.Constants.MOVIE_ID
import com.yunus.moviedb.control.FragmentController
import com.yunus.moviedb.feature.dashboard.MovieDetailsFragment

class MovieDetailsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        var bundle = Bundle()
        bundle.putInt(MOVIE_ID, intent.getIntExtra(MOVIE_ID, 0))
        bundle.putBoolean(IS_LIKED, intent.getBooleanExtra(IS_LIKED, false))
        FragmentController.navigateTo(this, MovieDetailsFragment::class.java.name, bundle)
    }
}