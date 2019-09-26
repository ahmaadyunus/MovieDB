package com.yunus.moviedb.repository

import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.data.PopularMovieResponse
import com.yunus.moviedb.extension.getStringResWithAppContext
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class DashboardRepository(private val serviceApi: ServiceApi) {

    fun getMovieList(
        page: String,
        cbOnResult: (PopularMovieResponse?) -> Unit,
        cbOnError: (Throwable?) -> Unit
    ) {
        getTrendingRepoAPI(page).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<PopularMovieResponse>() {
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

                override fun onNext(response: PopularMovieResponse) {
                    cbOnResult(response)
                }
            })
    }

    private fun getTrendingRepoAPI(page: String): Observable<PopularMovieResponse> {
        return serviceApi.getPopularMovies(BuildConfig.API_KEY, page)
    }
}