package com.yunus.moviedb.extension

import android.app.Application
import android.content.Context
import com.yunus.moviedb.base.Constants.EMPTY
import com.yunus.moviedb.base.Constants.EMPTY_CHECK
import org.koin.core.context.GlobalContext

fun getStringResWithAppContext(id: String,defaultVal:String?=id) = GlobalContext.get().koin.get<Application>().getStringRes(id,defaultVal)

fun Context?.getStringRes(id: String, defaultVal: String?=null): String = try {
    if (this == null) id
    else {
        val result = getStringRes(resources.getIdentifier(id, "string", packageName))
        if (result.isEmpty()) defaultVal?.let { it } ?: id else result
    }
} catch (e: Exception) {
    id
}

fun Context?.getStringRes(id: Int): String = try {
    when {
        id == 0 -> EMPTY
        this != null && resources.getString(id) != EMPTY_CHECK -> resources.getString(id)
        else -> EMPTY
    }
} catch (e: Exception) {
    id.toString()
}