package com.yunus.moviedb.extension

fun String?.filterEmpty(defaultValue: String = ""): String {
    return this ?: defaultValue
}

fun Double?.voteAveragePrefix(): String {
    return this?.formatDecimal().plus("/10")
}

fun String?.votesPrefix(): String {
    return this?.plus(" ")?.plus(getStringResWithAppContext("votes")) ?: ""
}

fun String?.shareStringPrefix(): String {
    return getStringResWithAppContext("share_prefix").plus(this)
}

