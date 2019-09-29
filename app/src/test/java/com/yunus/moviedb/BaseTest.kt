package com.yunus.moviedb

import androidx.test.core.app.ApplicationProvider
import com.yunus.moviedb.module.appModule
import com.yunus.moviedb.module.appRepoModule
import com.yunus.moviedb.module.appViewModelModule
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class BaseTest: KoinTest {
    val context = ApplicationProvider.getApplicationContext() as App

    @Before
    open fun beforeEach() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(context)
                modules(appModule, appViewModelModule, appRepoModule)
            }
        }

    }

    @After
    open fun afterEach() {
        stopKoin()
        try {
            Mockito.validateMockitoUsage()
        } catch (t: Throwable) {
            println("There could be no Mockito usage to validate")
        }
    }

    fun getApplication(): App {
        return context
    }
}