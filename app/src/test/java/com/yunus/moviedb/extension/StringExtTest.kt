package com.yunus.moviedb.extension

import com.yunus.moviedb.BaseTest
import org.junit.Assert
import org.junit.Test

class StringExtTest: BaseTest() {
    @Test
    fun `should return empty string if null`(){
        var string: String? = null
        Assert.assertEquals("", string.filterEmpty())
        Assert.assertNotEquals(null, string.filterEmpty())
    }

    @Test
    fun `should return string with vote average prefix`(){
        Assert.assertEquals("6.3/10", 6.3.voteAveragePrefix())
        Assert.assertNotEquals("6.30/10", 6.3.voteAveragePrefix())
        Assert.assertNotEquals("6/10", 6.3.voteAveragePrefix())
    }

    @Test
    fun `should return string with vote prefix`(){
        Assert.assertEquals("1000 Votes", "1000".votesPrefix())
        Assert.assertNotEquals("1000Votes", "1000".votesPrefix())
        Assert.assertNotEquals("1000 Vote", "1000".votesPrefix())
        Assert.assertNotEquals("1000", "1000".votesPrefix())
    }

    @Test
    fun `should return string with share prefix`(){
        val link = "http://link.web.com"
        Assert.assertEquals("Hi, please find this cool movie : \n".plus(link), link.shareStringPrefix())
        Assert.assertNotEquals("Hi, please find this cool movie : \n", link.shareStringPrefix())
        Assert.assertNotEquals(link, link.shareStringPrefix())
    }
}