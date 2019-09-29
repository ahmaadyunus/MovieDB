package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.base.Constants.FAVOURITE
import com.yunus.moviedb.base.Constants.MOVIE_TYPE
import com.yunus.moviedb.base.Constants.POPULAR
import com.yunus.moviedb.base.Constants.TOP_RATED
import com.yunus.moviedb.databinding.FragmentDashboardBinding
import com.yunus.moviedb.extension.getStringResWithAppContext
import com.yunus.moviedb.feature.common.PagerAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class DashboardFragment : BaseFragment() {
    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTabAndPager()

        viewModel.favouriteCount.observeForever {
            val badge = tab_layout.getTabAt(2)?.customView?.findViewById<TextView>(R.id.badge)
            val badgeContainer = tab_layout.getTabAt(2)?.customView?.findViewById<LinearLayout>(R.id.badge_container)
            badge?.text = it.toString()
            if (it > 0) {
                badgeContainer?.visibility = View.VISIBLE
            } else {
                badgeContainer?.visibility = View.GONE
            }
        }
    }

    private fun setTabAndPager() {
        pagerAdapter = PagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(getFragment(POPULAR), getStringResWithAppContext("popular"))
        pagerAdapter.addFragment(getFragment(TOP_RATED), getStringResWithAppContext("top_rated"))
        pagerAdapter.addFragment(getFragment(FAVOURITE), getStringResWithAppContext("favourite"))

        vp_dashboard.adapter = pagerAdapter
        vp_dashboard.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        vp_dashboard.offscreenPageLimit = 2

        tab_layout.setupWithViewPager(vp_dashboard)
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp_dashboard.currentItem = tab.position
            }
        })

        pagerAdapter.mFragmentTitleList.forEachIndexed { index: Int, text: String ->
            tab_layout.getTabAt(index)?.setCustomView(R.layout.view_tab)
            val title = tab_layout.getTabAt(index)?.customView?.findViewById<TextView>(R.id.tab_title)
            title?.text = text
        }

    }

    private fun getFragment(movieType: String): Fragment {
        val fragment = MovieListFragment()
        val args = Bundle()
        args.putString(MOVIE_TYPE, movieType)
        fragment.arguments = args
        return fragment
    }
}