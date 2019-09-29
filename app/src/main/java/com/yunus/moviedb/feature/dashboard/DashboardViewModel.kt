package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseViewModel
import com.yunus.moviedb.base.Constants.FAVOURITE
import com.yunus.moviedb.base.Constants.POPULAR
import com.yunus.moviedb.base.Constants.TOP_RATED
import com.yunus.moviedb.data.Genre
import com.yunus.moviedb.data.Movie
import com.yunus.moviedb.data.MovieDetailsResponse
import com.yunus.moviedb.data.Session
import com.yunus.moviedb.feature.common.SimpleViewModel
import com.yunus.moviedb.repository.DashboardRepository
import org.koin.core.inject

open class DashboardViewModel(open val app: Application) : BaseViewModel(app) {
    val repository: DashboardRepository by inject()
    var favouriteCount = MutableLiveData<Int>()
    private var popularList = mutableListOf<SimpleViewModel>()
    private var topRatedList = mutableListOf<SimpleViewModel>()
    private var favouriteList = mutableListOf<SimpleViewModel>()
    var genreList = mutableListOf<Genre>()
    private var popularPage = 1
    private var topRatedPage = 1
    private var favouritedPage = 1
    private var totalPopularPage = 1
    private var totalTopRatedPage = 1
    private var totalFavouritePage = 1

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

    fun updateTotalPage(movieType: String?, totalPage: Int) {
        when (movieType) {
            POPULAR -> totalPopularPage = totalPage
            TOP_RATED -> totalTopRatedPage = totalPage
            FAVOURITE -> totalFavouritePage = totalPage
        }
    }

    fun isLastPage(movieType: String?): Boolean {
        return when (movieType) {
            POPULAR -> popularPage > totalPopularPage + 1
            TOP_RATED -> topRatedPage > totalTopRatedPage + 1
            FAVOURITE -> favouritedPage > totalFavouritePage + 1
            else -> true
        }
    }


    fun getMovies(movieType: String?, page: Int, cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit, cbChange: () -> Unit, cbOnClick: (Int?, Boolean) -> Unit) {
        if (genreList.isEmpty()) {
            getGenres({
                prepareGetMovieList(movieType, page, cbOnSuccess, cbOnError, cbChange, cbOnClick = cbOnClick)
            }, {
                cbOnError(it)
            })
        } else {
            prepareGetMovieList(movieType, page, cbOnSuccess, cbOnError, cbChange, cbOnClick = cbOnClick)
        }
    }

    fun getGenres(cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        repository.getGenres({
            genreList = it?.genres?.toMutableList() ?: mutableListOf()
            cbOnSuccess()
        }, {
            cbOnError(it)
        })
    }

    fun prepareGetMovieList(movieType: String?, page: Int, cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit, cbChange: () -> Unit, cbOnClick: (Int?, Boolean) -> Unit) {
        if (movieType == FAVOURITE) {
            if (simplePreferences.isExpiredToken()) {
                createSession({
                    getMovieList(movieType, page, cbOnSuccess, cbOnError, sessionId = simplePreferences.getSessionId(), cbChange = cbChange, cbOnClick = cbOnClick)
                }, cbOnError)
            } else {
                getMovieList(movieType, page, cbOnSuccess, cbOnError, simplePreferences.getSessionId(), cbChange = cbChange, cbOnClick = cbOnClick)
            }
        } else {
            getMovieList(movieType, page, cbOnSuccess, cbOnError, cbChange = cbChange, cbOnClick = cbOnClick)
        }
    }

    fun createSession(cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        repository.createRequestToken({ response ->
            val session = Session().apply {
                sessionId = response?.sessionId
                expireAt = response?.expireAt
            }
            simplePreferences.saveSession(session)
            cbOnSuccess.invoke()
        }, {
            cbOnError(it)
        })
    }

    fun getMovieList(movieType: String?, page: Int, cbOnSuccess: () -> Unit, cbOnError: (Throwable?) -> Unit, sessionId: String? = "", cbChange: () -> Unit, cbOnClick: (Int?, Boolean) -> Unit) {
        repository.getMovieList(sessionId, movieType, page, {
            if (page == 1) {
                clearMovieList(movieType)
            }
            it?.movies?.forEach { movie ->
                addMovieToList(movieType, movie, getMovieGenre(movie), cbChange, cbOnClick)
            }
            if (movieType.equals(FAVOURITE)) {
                if (page == 1) {
                    favouriteCount.value = 0
                }
                favouriteCount.value =
                    it?.movies?.size?.let { it1 -> favouriteCount.value?.plus(it1) }
            }
            it?.totalPage?.let { it1 -> updateTotalPage(movieType, it1) }
            cbOnSuccess()
        }, { throwable ->
            cbOnError(throwable)
        })
    }

    fun clearMovieList(movieType: String?) {
        when (movieType) {
            POPULAR -> popularList.clear()
            TOP_RATED -> topRatedList.clear()
            FAVOURITE -> favouriteList.clear()
        }
    }

    fun addMovieToList(movieType: String?, movie: Movie?, genre: String?, cbChange: () -> Unit, cbOnClick: (Int?, Boolean) -> Unit) {
        when (movieType) {
            POPULAR -> popularList.add(
                MovieItemViewModel(movie?.id, movie?.posterPath, movie?.title, genre, isLiked = isFavourited(movie), cbOnClick = cbOnClick) { vm ->
                    val isLikedTemp = vm.isLiked
                    cbChange.invoke()
                    makeFavourite(vm.movieId, !isLikedTemp, {}, {
                        vm.isLiked = isLikedTemp
                        cbChange.invoke()
                    })
                })
            TOP_RATED -> topRatedList.add(
                MovieItemViewModel(movie?.id, movie?.posterPath, movie?.title, genre, isLiked = isFavourited(movie),  cbOnClick = cbOnClick) { vm ->
                    val isLikedTemp = vm.isLiked
                    cbChange.invoke()
                    makeFavourite(vm.movieId, !isLikedTemp, {}, {
                        vm.isLiked = isLikedTemp
                        cbChange.invoke()
                    })
                })
            FAVOURITE -> favouriteList.add(
                MovieItemViewModel(movie?.id, movie?.posterPath, movie?.title, isLiked = true, movieGenre = genre, cbOnClick = cbOnClick) { vm ->
                    val isLikedTemp = vm.isLiked
                    cbChange.invoke()
                    makeFavourite(vm.movieId, !isLikedTemp, {}, {
                        vm.isLiked = isLikedTemp
                        cbChange.invoke()
                    })
                })
        }
    }

    fun isFavourited(movie: Movie?): Boolean {
        return (favouriteList.find { (it as MovieItemViewModel).movieId == movie?.id })?.let {
            true
        } ?: false
    }

    fun getMovieGenre(movie: Movie): String? {
        var genre = genreList.find { it.id == movie.genreIds[0] }?.name
        if (movie.genreIds.size > 1) {
            movie.genreIds.forEachIndexed { index, it1 ->
                if (index > 0) {
                    genre = genre.plus(", ")
                        .plus(genreList.find { it.id == movie.genreIds[index] }?.name)
                }
            }
        }
        return genre
    }

    fun makeFavourite(mediaId: Int?, isFavourite: Boolean?, cbOnResult: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        if (simplePreferences.isExpiredToken()) {
            createSession({
                repository.makeFavourite(simplePreferences.getSessionId(), mediaId, isFavourite, cbOnResult, cbOnError)
            }, cbOnError)
        } else {
            repository.makeFavourite(simplePreferences.getSessionId(), mediaId, isFavourite, cbOnResult, cbOnError)
        }
    }
}