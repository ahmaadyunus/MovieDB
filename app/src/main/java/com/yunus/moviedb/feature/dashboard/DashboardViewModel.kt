package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yunus.moviedb.base.BaseViewModel

class DashboardViewModel(val app: Application): BaseViewModel(app) {
    var favouriteCount = MutableLiveData<Int>()
}