package com.yunus.moviedb.util

import com.yunus.moviedb.BaseTest
import org.junit.Assert
import org.junit.Test
import java.util.*

class DateUtilTest: BaseTest() {
    @Test
    fun `should return date string with formatted`(){
        Assert.assertEquals("22 May 2019", DateUtils.formateDate( "2019-05-22", DateUtils.YYYY_MM_DD, DateUtils.DD_MMM_YYYY))
        Assert.assertNotEquals("22May2019", DateUtils.formateDate( "2019-05-22", DateUtils.YYYY_MM_DD, DateUtils.DD_MMM_YYYY))
        Assert.assertNotEquals("22052019", DateUtils.formateDate( "2019-05-22", DateUtils.YYYY_MM_DD, DateUtils.DD_MMM_YYYY))
        Assert.assertNotEquals("2019-05-22", DateUtils.formateDate( "2019-05-22", DateUtils.YYYY_MM_DD, DateUtils.DD_MMM_YYYY))
    }

    @Test
    fun `should return string as Date`(){
        val date = Date(1558530000000)
        Assert.assertEquals(date.time, DateUtils.stringToDate("2019-05-22 13:00:00 UTC", DateUtils.YYY_MM_DD_HH_MM_SS_Z).time)
    }
}