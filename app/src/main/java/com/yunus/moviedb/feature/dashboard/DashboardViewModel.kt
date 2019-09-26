package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yunus.moviedb.base.BaseViewModel
import com.yunus.moviedb.base.Constants.POPULAR
import com.yunus.moviedb.base.Constants.TOP_RATED
import com.yunus.moviedb.feature.common.SimpleViewModel
import com.yunus.moviedb.repository.DashboardRepository
import org.koin.core.inject

class DashboardViewModel(val app: Application) : BaseViewModel(app) {
    val repository: DashboardRepository by inject()
    var favouriteCount = MutableLiveData<Int>()
    var popularList = mutableListOf<SimpleViewModel>()
    var popularPage = 1
    var topRatedPage = 1

    fun resetPage(movieType: String?) {
        when (movieType) {
            POPULAR -> popularPage = 1
            TOP_RATED -> topRatedPage = 1
        }
    }

    fun updatePage(movieType: String?) {
        when (movieType) {
            POPULAR -> popularPage++
            TOP_RATED -> topRatedPage++
        }
    }

    fun getPage(movieType: String?): Int {
        return when (movieType) {
            POPULAR -> popularPage
            TOP_RATED -> topRatedPage
            else -> 1
        }
    }

    fun getMovies(
        movieType: String?,
        page: Int,
        cbOnSuccess: () -> Unit,
        cbOnError: (Throwable?) -> Unit
    ) {
        repository.getMovieList(movieType, page, {
            if (page == 1 ){
                popularList.clear()
            }
            it?.movies?.forEach { movie ->
                popularList.add(MovieItemViewModel(movie.posterPath, movie.title, ""))
            }
            favouriteCount.value = it?.movies?.size
            cbOnSuccess()
        }, { throwable ->
            cbOnError(throwable)
        })
    }
}