package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseActivity
import com.yunus.moviedb.control.FragmentController

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        FragmentController.navigateTo(this, DashboardFragment::class.java.name, Bundle())

    }
}