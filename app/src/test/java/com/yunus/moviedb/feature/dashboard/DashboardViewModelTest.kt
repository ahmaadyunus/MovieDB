package com.yunus.moviedb.feature.dashboard

import com.yunus.moviedb.BaseTest
import com.yunus.moviedb.base.Constants.FAVOURITE
import com.yunus.moviedb.base.Constants.POPULAR
import com.yunus.moviedb.base.Constants.TOP_RATED
import com.yunus.moviedb.data.Genre
import com.yunus.moviedb.data.Movie
import com.yunus.moviedb.data.Session
import org.amshove.kluent.mock
import org.junit.Assert
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DashboardViewModelTest: BaseTest() {
    private val viewModel: DashboardViewModel by inject()

    @Test
    fun resetPageTest(){
        viewModel.popularPage = 20
        viewModel.topRatedPage = 20
        viewModel.favouritedPage = 20

        viewModel.resetPage(POPULAR)
        Assert.assertEquals(1, viewModel.popularPage)
        Assert.assertNotEquals(20, viewModel.popularPage)
        Assert.assertNotEquals(1, viewModel.topRatedPage)
        Assert.assertNotEquals(1, viewModel.favouritedPage)

        viewModel.popularPage = 20
        viewModel.topRatedPage = 20
        viewModel.favouritedPage = 20

        viewModel.resetPage(TOP_RATED)
        Assert.assertEquals(1, viewModel.topRatedPage)
        Assert.assertNotEquals(20, viewModel.topRatedPage)
        Assert.assertNotEquals(1, viewModel.popularPage)
        Assert.assertNotEquals(1, viewModel.favouritedPage)

        viewModel.popularPage = 20
        viewModel.topRatedPage = 20
        viewModel.favouritedPage = 20

        viewModel.resetPage(FAVOURITE)
        Assert.assertEquals(1, viewModel.favouritedPage)
        Assert.assertNotEquals(20, viewModel.favouritedPage)
        Assert.assertNotEquals(1, viewModel.topRatedPage)
        Assert.assertNotEquals(1, viewModel.popularPage)

    }

    @Test
    fun updatePageTest(){
        viewModel.popularPage = 1
        viewModel.topRatedPage = 1
        viewModel.favouritedPage = 1

        viewModel.updatePage(POPULAR)
        Assert.assertEquals(2, viewModel.popularPage)
        Assert.assertNotEquals(1, viewModel.popularPage)
        Assert.assertNotEquals(2, viewModel.topRatedPage)
        Assert.assertNotEquals(2, viewModel.favouritedPage)

        viewModel.popularPage = 1
        viewModel.topRatedPage = 1
        viewModel.favouritedPage = 1

        viewModel.updatePage(TOP_RATED)
        Assert.assertEquals(2, viewModel.topRatedPage)
        Assert.assertNotEquals(1, viewModel.topRatedPage)
        Assert.assertNotEquals(2, viewModel.popularPage)
        Assert.assertNotEquals(2, viewModel.favouritedPage)

        viewModel.popularPage = 1
        viewModel.topRatedPage = 1
        viewModel.favouritedPage = 1

        viewModel.updatePage(FAVOURITE)
        Assert.assertEquals(2, viewModel.favouritedPage)
        Assert.assertNotEquals(1, viewModel.favouritedPage)
        Assert.assertNotEquals(2, viewModel.topRatedPage)
        Assert.assertNotEquals(2, viewModel.popularPage)

    }

    @Test
    fun getPageTest(){
        viewModel.popularPage = 1
        viewModel.topRatedPage = 2
        viewModel.favouritedPage = 3

        Assert.assertEquals(1, viewModel.getPage(POPULAR))
        Assert.assertEquals(2, viewModel.getPage(TOP_RATED))
        Assert.assertEquals(3, viewModel.getPage(FAVOURITE))
        Assert.assertNotEquals(3, viewModel.getPage(POPULAR))
        Assert.assertNotEquals(1, viewModel.getPage(TOP_RATED))
        Assert.assertNotEquals(2, viewModel.getPage(FAVOURITE))
        Assert.assertNotEquals(2, viewModel.getPage(POPULAR))
        Assert.assertNotEquals(3, viewModel.getPage(TOP_RATED))
        Assert.assertNotEquals(1, viewModel.getPage(FAVOURITE))
    }

    @Test
    fun getListTest(){
        val movieItem = mock(MovieItemViewModel::class)
        viewModel.popularList.add(movieItem)

        viewModel.topRatedList.add(movieItem)
        viewModel.topRatedList.add(movieItem)

        viewModel.favouriteList.add(movieItem)
        viewModel.favouriteList.add(movieItem)
        viewModel.favouriteList.add(movieItem)

        Assert.assertEquals(1, viewModel.getList(POPULAR).size)
        Assert.assertEquals(2, viewModel.getList(TOP_RATED).size)
        Assert.assertEquals(3, viewModel.getList(FAVOURITE).size)
        Assert.assertNotEquals(3, viewModel.getList(POPULAR).size)
        Assert.assertNotEquals(1, viewModel.getList(TOP_RATED).size)
        Assert.assertNotEquals(2, viewModel.getList(FAVOURITE).size)
        Assert.assertNotEquals(2, viewModel.getList(POPULAR).size)
        Assert.assertNotEquals(3, viewModel.getList(TOP_RATED).size)
        Assert.assertNotEquals(1, viewModel.getList(FAVOURITE).size)

    }

    @Test
    fun updateTotalPageTest(){
        viewModel.totalPopularPage = 1
        viewModel.totalTopRatedPage = 1
        viewModel.totalFavouritePage = 1

        viewModel.updateTotalPage(POPULAR, 200)
        Assert.assertEquals(200, viewModel.totalPopularPage)
        Assert.assertNotEquals(1, viewModel.totalPopularPage)
        Assert.assertNotEquals(200, viewModel.totalTopRatedPage)
        Assert.assertNotEquals(200, viewModel.totalFavouritePage)

        viewModel.totalPopularPage = 1
        viewModel.totalTopRatedPage = 1
        viewModel.totalFavouritePage = 1

        viewModel.updateTotalPage(TOP_RATED, 200)
        Assert.assertEquals(200, viewModel.totalTopRatedPage)
        Assert.assertNotEquals(1, viewModel.totalTopRatedPage)
        Assert.assertNotEquals(200, viewModel.totalPopularPage)
        Assert.assertNotEquals(200, viewModel.totalFavouritePage)

        viewModel.totalPopularPage = 1
        viewModel.totalTopRatedPage = 1
        viewModel.totalFavouritePage = 1

        viewModel.updateTotalPage(FAVOURITE, 200)
        Assert.assertEquals(200, viewModel.totalFavouritePage)
        Assert.assertNotEquals(1, viewModel.totalFavouritePage)
        Assert.assertNotEquals(200, viewModel.totalTopRatedPage)
        Assert.assertNotEquals(200, viewModel.totalPopularPage)

    }

    @Test
    fun isLastPageTest(){
        viewModel.popularPage = 3
        viewModel.topRatedPage = 3
        viewModel.favouritedPage = 3
        viewModel.totalPopularPage = 1
        viewModel.totalTopRatedPage = 1
        viewModel.totalFavouritePage = 1

        assertTrue(viewModel.isLastPage(POPULAR))
        assertTrue(viewModel.isLastPage(TOP_RATED))
        assertTrue(viewModel.isLastPage(FAVOURITE))
    }

    @Test
    fun prepareGetMovieListTest(){
        var session = mock(Session::class)
        Mockito.`when`(session.sessionId).thenReturn("sessionid")
        Mockito.`when`(session.expireAt).thenReturn("2019-05-22 13:00:00 UTC")
        viewModel.simplePreferences.saveSession(session)

    }

    @Test
    fun isFavouritedTest(){
        val movieItem1 = mock(MovieItemViewModel::class)
        Mockito.`when`(movieItem1.movieId).thenReturn(1)
        viewModel.favouriteList.add(movieItem1)

        val movie = mock(Movie::class)
        Mockito.`when`(movie.id).thenReturn(1)
        val movie2 = mock(Movie::class)
        Mockito.`when`(movie2.id).thenReturn(2)
        assertTrue(viewModel.isFavourited(movie))
        assertFalse(viewModel.isFavourited(movie2))
    }

    @Test
    fun getMovieGenreTest(){
        val genre1 = mock(Genre::class)
        Mockito.`when`(genre1.id).thenReturn(1)
        Mockito.`when`(genre1.name).thenReturn("Comedy")
        val genre2 = mock(Genre::class)
        Mockito.`when`(genre2.id).thenReturn(2)
        Mockito.`when`(genre2.name).thenReturn("Drama")

        viewModel.genreList.add(genre1)
        viewModel.genreList.add(genre2)

        val movie = mock(Movie::class)
        Mockito.`when`(movie.genreIds).thenReturn(mutableListOf(1,2))

        Assert.assertEquals("Comedy, Drama", viewModel.getMovieGenre(movie))

    }
}