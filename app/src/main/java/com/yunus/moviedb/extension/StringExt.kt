package com.yunus.moviedb.extension

fun String?.filterEmpty(defaultValue: String = ""): String {
    return this ?: defaultValue
}
