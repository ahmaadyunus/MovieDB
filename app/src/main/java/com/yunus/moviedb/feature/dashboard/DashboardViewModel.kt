package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yunus.moviedb.base.BaseViewModel
import com.yunus.moviedb.feature.common.SimpleViewModel
import com.yunus.moviedb.repository.DashboardRepository
import org.koin.core.inject

class DashboardViewModel(val app: Application) : BaseViewModel(app) {
    val repository: DashboardRepository by inject()
    var favouriteCount = MutableLiveData<Int>()
    var popularList = mutableListOf<SimpleViewModel>()

    fun getPopularMovies(page: String, cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        repository.getMovieList(page, {
            it?.movies?.forEach { movie ->
                popularList.add(MovieItemViewModel(movie.posterPath, movie.originalTitle, ""))
            }
            favouriteCount.value = it?.movies?.size
            cbOnSuccess()
        }, { throwable ->
            cbOnError(throwable)
        })
    }
}