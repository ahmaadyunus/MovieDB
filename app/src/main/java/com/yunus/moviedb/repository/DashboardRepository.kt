package com.yunus.moviedb.repository

import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.base.Constants
import com.yunus.moviedb.data.MoviesResponse
import com.yunus.moviedb.extension.getStringResWithAppContext
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class DashboardRepository(private val serviceApi: ServiceApi) {

    fun getMovieList(
        movieType: String?,
        page: Int,
        cbOnResult: (MoviesResponse?) -> Unit,
        cbOnError: (Throwable?) -> Unit
    ) {
        getTrendingRepoAPI(movieType, page).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MoviesResponse>() {
                override fun onComplete() {
                    dispose()
                }

                override fun onError(e: Throwable?) {
                    if (e is UnknownHostException) {
                        cbOnError(e)
                    } else {
                        cbOnError(Throwable(getStringResWithAppContext("something_went_wrong")))
                    }
                    dispose()
                }

                override fun onNext(response: MoviesResponse) {
                    cbOnResult(response)
                }
            })
    }

    private fun getTrendingRepoAPI(movieType: String?, page: Int): Observable<MoviesResponse> {
        return when(movieType) {
            Constants.POPULAR -> serviceApi.getPopularMovies(BuildConfig.API_KEY, page)
            Constants.TOP_RATED -> serviceApi.getTopRatedMovies(BuildConfig.API_KEY, page)
            Constants.FAVOURITE -> serviceApi.getFavorited(BuildConfig.API_KEY, "","created_at.asc",page)
            else -> serviceApi.getPopularMovies(BuildConfig.API_KEY, page)
        }
    }
}