package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yunus.moviedb.base.BaseViewModel
import com.yunus.moviedb.base.Constants.FAVOURITE
import com.yunus.moviedb.base.Constants.POPULAR
import com.yunus.moviedb.base.Constants.TOP_RATED
import com.yunus.moviedb.feature.common.SimpleViewModel
import com.yunus.moviedb.repository.DashboardRepository
import org.koin.core.inject

class DashboardViewModel(val app: Application) : BaseViewModel(app) {
    val repository: DashboardRepository by inject()
    var favouriteCount = MutableLiveData<Int>()
    var popularList = mutableListOf<SimpleViewModel>()
    var topRatedList = mutableListOf<SimpleViewModel>()
    var favouriteList = mutableListOf<SimpleViewModel>()
    var popularPage = 1
    var topRatedPage = 1
    var favouritedPage = 1

    fun resetPage(movieType: String?) {
        when (movieType) {
            POPULAR -> popularPage = 1
            TOP_RATED -> topRatedPage = 1
            FAVOURITE -> favouritedPage = 1
        }
    }

    fun updatePage(movieType: String?) {
        when (movieType) {
            POPULAR -> popularPage++
            TOP_RATED -> topRatedPage++
            FAVOURITE -> favouritedPage++
        }
    }

    fun getPage(movieType: String?): Int {
        return when (movieType) {
            POPULAR -> popularPage
            TOP_RATED -> topRatedPage
            FAVOURITE -> favouritedPage
            else -> 1
        }
    }

    fun getList(movieType: String?): List<SimpleViewModel> {
        return when (movieType) {
            POPULAR -> popularList
            TOP_RATED -> topRatedList
            FAVOURITE -> favouriteList
            else -> mutableListOf()
        }
    }


    fun getMovies(movieType: String?, page: Int, cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        repository.getMovieList(movieType, page, {
            if (page == 1) {
                when (movieType) {
                    POPULAR -> popularList.clear()
                    TOP_RATED -> topRatedList.clear()
                    FAVOURITE -> favouriteList.clear()
                }
            }
            it?.movies?.forEach { movie ->
                when (movieType) {
                    POPULAR ->  popularList.add(MovieItemViewModel(movie.posterPath, movie.title, ""))
                    TOP_RATED ->  topRatedList.add(MovieItemViewModel(movie.posterPath, movie.title, ""))
                    FAVOURITE ->  favouriteList.add(MovieItemViewModel(movie.posterPath, movie.title, ""))
                }
            }
            if (movieType.equals(FAVOURITE)) {
                favouriteCount.value = it?.movies?.size
            }
            cbOnSuccess()
        }, { throwable ->
            cbOnError(throwable)
        })
    }
}