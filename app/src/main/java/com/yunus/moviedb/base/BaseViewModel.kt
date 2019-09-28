package com.yunus.moviedb.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yunus.moviedb.storage.SimplePreferences
import org.koin.core.KoinComponent
import org.koin.core.get

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    val simplePreferences: SimplePreferences = get()

}