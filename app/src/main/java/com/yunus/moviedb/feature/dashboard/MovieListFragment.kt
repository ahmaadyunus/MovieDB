package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.databinding.FragmentMovieListBinding
import com.yunus.moviedb.feature.common.GenericAppAdapter
import com.yunus.moviedb.feature.common.SimpleViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.rv_movie_list as rvMovieList
import kotlinx.android.synthetic.main.fragment_movie_list.shimmer_layout as shimmerLayout

class MovieListFragment : BaseFragment() {
    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: GenericAppAdapter<SimpleViewModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getData()

    }

    private fun getData() {
        setShimmer(true)
        viewModel.getPopularMovies("1",{
            setShimmer(false)
            setupList()
        }, {
            setShimmer(false)
        })

    }

    fun setupList() {
        adapter = GenericAppAdapter(viewModel.popularList)
        val gridLayoutManager = GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false)
        rvMovieList.layoutManager = gridLayoutManager
        rvMovieList.adapter = adapter
        rvMovieList.setHasFixedSize(true)
    }

    private fun setShimmer(isStart: Boolean) {
        if (isStart) {
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
        } else {
            shimmerLayout.visibility = View.GONE
            shimmerLayout.stopShimmer()
        }
    }
}