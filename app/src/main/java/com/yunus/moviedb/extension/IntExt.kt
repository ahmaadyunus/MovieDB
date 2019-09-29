package com.yunus.moviedb.extension

fun Double.formatDecimal(decimalCount: Int = 1): String {
    return String.format("%." + decimalCount + "f", this)
}