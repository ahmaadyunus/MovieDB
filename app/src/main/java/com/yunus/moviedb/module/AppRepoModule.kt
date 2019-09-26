package com.yunus.moviedb.module

import com.yunus.moviedb.repository.DashboardRepository
import org.koin.dsl.module

val appRepoModule = module {
    single { DashboardRepository(get()) }
}