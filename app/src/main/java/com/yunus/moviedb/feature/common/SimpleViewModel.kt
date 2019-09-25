package com.yunus.moviedb.feature.common

import com.yunus.moviedb.R
import org.koin.core.KoinComponent

interface SimpleViewModel: KoinComponent {
    fun layoutId(): Int
    fun marginID(): Int { return R.dimen.space_0 }
}