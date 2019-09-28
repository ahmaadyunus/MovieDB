package com.yunus.moviedb.storage

import android.content.Context
import com.google.gson.Gson
import com.yunus.moviedb.base.Constants.SESSION
import com.yunus.moviedb.data.Session
import com.yunus.moviedb.util.DateUtils

class SimplePreferences (context: Context) {

    val sharedPref = context.getSharedPreferences("SimplePreference", Context.MODE_PRIVATE)

    fun saveSession(session: Session) {
        var sessionString = Gson().toJson(session)
        with(sharedPref.edit()){
            putString(SESSION, sessionString)
            apply()
        }

    }

    fun getSession() : Session {
        return Gson().fromJson(sharedPref.getString(SESSION, ""), Session::class.java)?.let { it }?: Session()
    }

    fun getSessionId() : String? {
        return Gson().fromJson(sharedPref.getString(SESSION, ""), Session::class.java).sessionId
    }

    fun isExpiredToken(): Boolean {
        val session = getSession()
        session.expireAt?.let {
            return if (it.isEmpty()) {
                true
            } else {
                val sessionExpireDate = DateUtils.stringToDate(it, DateUtils.YYY_MM_DD_HH_MM_SS_Z)
                System.currentTimeMillis() > sessionExpireDate.time
            }
        }?: return true
    }
}