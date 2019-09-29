package com.yunus.moviedb.control

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.core.KoinComponent

object ActivityController: KoinComponent {
    fun navigateToWithBundle(from: Activity?, to: Class<out Any>, finish: Boolean = false, bundle: Bundle?, requestCode: Int = -1, fragment: Fragment? = null) {
        val intent = Intent(from, to)
        if (bundle != null)
            intent.putExtras(bundle)
            from?.startActivity(intent)
        if (finish) from?.finish()
    }
}