package com.yunus.moviedb

import androidx.multidex.MultiDexApplication
import com.yunus.moviedb.module.appModule
import com.yunus.moviedb.module.appRepoModule
import com.yunus.moviedb.module.appViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

open class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupConfiguration()
    }

    fun setupConfiguration() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidContext(this@App)
                modules(appModule, appViewModelModule, appRepoModule)
            }
        }
    }
}
