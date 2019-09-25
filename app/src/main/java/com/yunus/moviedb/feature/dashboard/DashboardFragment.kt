package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.databinding.FragmentDashboardBinding
import kotlinx.android.synthetic.main.fragment_dashboard.shimmer_layout as shimerLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DashboardFragment: BaseFragment(){
    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        shimerLayout.startShimmer()
    }
}