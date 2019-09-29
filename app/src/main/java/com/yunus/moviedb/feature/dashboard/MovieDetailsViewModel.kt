package com.yunus.moviedb.feature.dashboard

import android.app.Application
import androidx.databinding.ObservableField
import com.yunus.moviedb.extension.filterEmpty
import com.yunus.moviedb.extension.formatDecimal
import com.yunus.moviedb.extension.voteAveragePrefix
import com.yunus.moviedb.extension.votesPrefix
import com.yunus.moviedb.util.DateUtils

class MovieDetailsViewModel(override val app: Application) : DashboardViewModel(app) {

    var movieId: Int? = 0
    var urlImgBackdrop = ObservableField("")
    var urlImgPoster = ObservableField("")
    var movieTitle = ObservableField("")
    var voteAverage = ObservableField("")
    var movieVote = ObservableField("")
    var movieReleaseDate = ObservableField("")
    var movieLanguage = ObservableField("")
    var movieOverview = ObservableField("")
    var homePage = ObservableField("")
    var isFavourited = ObservableField(false)


    fun getMovieDetail(movieId: Int?, cbOnResult: () -> Unit, cbOnError: (Throwable?) -> Unit) {
        repository.getMovieDetails(movieId, {
            urlImgBackdrop.set(it?.backdropPath.filterEmpty())
            urlImgPoster.set(it?.posterPath.filterEmpty())
            movieTitle.set(it?.title.filterEmpty())
            voteAverage.set(it?.voteAverage.voteAveragePrefix().filterEmpty())
            movieVote.set(it?.voteCount?.formatDecimal(0).votesPrefix().filterEmpty())
            movieReleaseDate.set((it?.releaseDate?.let { it1 ->
                DateUtils.formateDate(
                    it1,
                    DateUtils.YYYY_MM_DD,
                    DateUtils.DD_MMM_YYYY
                )
            }).filterEmpty())
            var movieLanguageTemp = it?.spokenLanguage?.get(0)?.name
            it?.spokenLanguage?.forEachIndexed { index, language ->
                if (index > 0) {
                    movieLanguageTemp = movieLanguageTemp.plus(", ").plus(language.name)
                }
            }
            movieLanguage.set(movieLanguageTemp.filterEmpty())
            movieOverview.set(it?.overview.filterEmpty())
            homePage.set(it?.homePage.filterEmpty())
            cbOnResult.invoke()
        }, cbOnError)
    }

    fun onLike() {
        isFavourited.get()?.let { isFavourited.set(!it) }
        makeFavourite(movieId, isFavourited.get(), {}, {
            isFavourited.get()?.let { isFavourited.set(!it) }})
    }
}