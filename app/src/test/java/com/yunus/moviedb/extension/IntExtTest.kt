package com.yunus.moviedb.extension

import com.yunus.moviedb.BaseTest
import org.junit.Assert
import org.junit.Test

class IntExtTest: BaseTest() {
    @Test
    fun `should return double with decimal format`(){
        Assert.assertEquals("20.00", 20.0.formatDecimal(2))
        Assert.assertEquals("20.0", 20.0.formatDecimal(1))
        Assert.assertEquals("20", 20.0.formatDecimal(0))
        Assert.assertNotEquals("20.0", 20.0.formatDecimal(0))
    }
}