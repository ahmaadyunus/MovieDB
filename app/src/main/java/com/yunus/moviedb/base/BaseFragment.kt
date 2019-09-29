package com.yunus.moviedb.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yunus.moviedb.control.FragmentController

open class BaseFragment : Fragment() {
    lateinit var mActivity: BaseActivity

    val bundle = Bundle()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
    }

    fun goTBack() {
        FragmentController.popBack(activity)
    }

}