package com.yunus.moviedb.storage

import com.yunus.moviedb.BaseTest
import com.yunus.moviedb.data.Session
import org.junit.Test
import kotlin.test.assertTrue

class SimplePreferenceTest: BaseTest() {

    @Test
    fun isExpiredTokenTest(){
        var session = Session().apply { sessionId = "sessionid"
                                    expireAt = "2019-05-22 13:00:00 UTC"
        }
        var simplePreferences = SimplePreferences(context)
        simplePreferences.saveSession(session)

        assertTrue(simplePreferences.isExpiredToken())
    }
}