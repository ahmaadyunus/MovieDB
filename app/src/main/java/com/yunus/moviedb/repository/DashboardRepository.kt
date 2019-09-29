package com.yunus.moviedb.repository

import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.base.Constants
import com.yunus.moviedb.base.Constants.MOVIE
import com.yunus.moviedb.data.*
import com.yunus.moviedb.extension.getStringResWithAppContext
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import retrofit2.HttpException
import java.net.UnknownHostException

class DashboardRepository(private val serviceApi: ServiceApi) {

    fun getMovieList(
        sessionId: String?,
        movieType: String?,
        page: Int,
        cbOnResult: (MoviesResponse?) -> Unit,
        cbOnError: (Throwable?) -> Unit
    ) {
        getMovieListAPI(sessionId, movieType, page).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MoviesResponse>() {
                override fun onComplete() {
                    dispose()
                }

                override fun onError(e: Throwable?) {
                    if (e is UnknownHostException || e is HttpException) {
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

    private fun getMovieListAPI(sessionId:String?, movieType: String?, page: Int): Observable<MoviesResponse> {
        return when (movieType) {
            Constants.POPULAR -> serviceApi.getPopularMovies(BuildConfig.API_KEY, page)
            Constants.TOP_RATED -> serviceApi.getTopRatedMovies(BuildConfig.API_KEY, page)
            Constants.FAVOURITE -> serviceApi.getFavorited(
                BuildConfig.API_KEY,
                sessionId,
                "created_at.asc",
                page
            )
            else -> serviceApi.getPopularMovies(BuildConfig.API_KEY, page)
        }
    }

    fun getGenres(cbOnResult: (GenresResponse?) -> Unit, cbOnError: (Throwable?) -> Unit) {
        getGenresAPI().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<GenresResponse>() {
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

                override fun onNext(value: GenresResponse?) {
                    cbOnResult(value)
                }
            })

    }

    private fun getGenresAPI(): Observable<GenresResponse> {
        return serviceApi.getGenres(BuildConfig.API_KEY)
    }

    fun createRequestToken(cbOnResult: (CreateRequestTokenResponse?) -> Unit, cbOnError: (Throwable?) -> Unit) {
        createRequestTokenAPI().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CreateRequestTokenResponse>() {
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

                override fun onNext(value: CreateRequestTokenResponse?) {
                    value?.requestToken?.let { createSessionWithLogin(it, cbOnResult, cbOnError) }
                }
            })
    }

    private fun createRequestTokenAPI(): Observable<CreateRequestTokenResponse> {
        return serviceApi.createRequestToken(BuildConfig.API_KEY)
    }

    fun createSessionWithLogin(requestToken: String, cbOnResult: (CreateRequestTokenResponse?) -> Unit, cbOnError: (Throwable?) -> Unit) {
        createSessionWithLoginAPI(requestToken).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CreateRequestTokenResponse>() {
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

                override fun onNext(value: CreateRequestTokenResponse?) {
                    value?.requestToken?.let { createSession(it, { response ->
                        response?.expireAt = value.expireAt
                        cbOnResult(response)
                    }, cbOnError) }
                }
            })
    }

    private fun createSessionWithLoginAPI(requestToken: String): Observable<CreateRequestTokenResponse> {
        val request = CreateSessionLoginRequest(BuildConfig.USERNAME, BuildConfig.PASSWORD, requestToken)
        return serviceApi.createSessionWithLogin(BuildConfig.API_KEY, request)
    }

    fun createSession(requestToken: String, cbOnResult: (CreateRequestTokenResponse?) -> Unit, cbOnError: (Throwable?) -> Unit) {
        createSessionAPI(requestToken).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<CreateRequestTokenResponse>() {
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

                override fun onNext(value: CreateRequestTokenResponse?) {
                    cbOnResult(value)
                }
            })
    }

    private fun createSessionAPI(requestToken: String): Observable<CreateRequestTokenResponse> {
        val request = CreateSessionLoginRequest(requestToken = requestToken)
        return serviceApi.createSession(BuildConfig.API_KEY, request)
    }

    fun makeFavourite(sessionId: String?, mediaId: Int?, isFavourite: Boolean?, cbOnResult:() -> Unit, cbOnError: (Throwable?) -> Unit){
        makeFavouriteAPI(sessionId, mediaId, isFavourite).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MakeFavouriteResponse>() {
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

                override fun onNext(value: MakeFavouriteResponse?) {
                    cbOnResult()
                }
            })
    }

    private fun makeFavouriteAPI(sessionId: String?, mediaId: Int?, isFavourite: Boolean?): Observable<MakeFavouriteResponse>{
        val request = MakeFavouriteRequest(MOVIE, mediaId, isFavourite)
        return serviceApi.makeFavourite(BuildConfig.API_KEY, sessionId, request)
    }

    fun getMovieDetails(movieId: Int?, cbOnResult:(MovieDetailsResponse?) -> Unit, cbOnError: (Throwable?) -> Unit){
        getMovieDetailsAPI(movieId).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MovieDetailsResponse>() {
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

                override fun onNext(value: MovieDetailsResponse?) {
                    cbOnResult(value)
                }
            })
    }

    private fun getMovieDetailsAPI(movieId: Int?): Observable<MovieDetailsResponse>{
        return serviceApi.getMovieDetails(movieId, BuildConfig.API_KEY)
    }
}