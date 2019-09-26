package com.yunus.moviedb.module

import com.yunus.moviedb.feature.dashboard.DashboardViewModel
import org.koin.dsl.module

val appViewModelModule = module {
    single { DashboardViewModel(get())}
}