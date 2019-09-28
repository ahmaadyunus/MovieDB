package com.yunus.moviedb.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm"
    const val EEEE_DD_MM_YYYY = "EEEE, dd MMM yyyy"
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val EEEE = "EEEE"
    const val YYY_MM_DD_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss z"

    fun formateDate(dateString: String?, initialPattern: String, newPattern: String): String {
        if (dateString == null)
            return ""
        if (dateString == "00000000" || dateString == "000000000000")
            return ""
        val initialFormatter = SimpleDateFormat(initialPattern, Locale.UK)
        val newFormatter = SimpleDateFormat(newPattern, Locale.UK)
        return try {
            val date = initialFormatter.parse(dateString)
            newFormatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun stringToDate(dateString: String?, pattern: String): Date {
        if (dateString == null)
            return Date()

        val formatter = SimpleDateFormat(pattern, Locale.UK)
        return try {
            formatter.parse(dateString)
        } catch (e: Exception) {
            Date()
        }
    }

}