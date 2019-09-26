package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.paginate.Paginate
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.base.Constants.MOVIE_TYPE
import com.yunus.moviedb.base.Constants.PAGE_LIMIT
import com.yunus.moviedb.databinding.FragmentMovieListBinding
import com.yunus.moviedb.feature.common.GenericAppAdapter
import com.yunus.moviedb.feature.common.SimpleViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.rv_movie_list as rvMovieList
import kotlinx.android.synthetic.main.fragment_movie_list.shimmer_layout as shimmerLayout
import kotlinx.android.synthetic.main.fragment_movie_list.layout_error as layoutError


class MovieListFragment : BaseFragment(), Paginate.Callbacks {
    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: GenericAppAdapter<SimpleViewModel>
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.resetPage(arguments?.getString(MOVIE_TYPE, ""))
        getData(viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")))
    }

    private fun getData(page: Int) {
        isLoading = true
        layoutError.visibility = View.GONE
        if (page == 1) {
            setShimmer(true)
        }
        viewModel.getMovies(arguments?.getString(MOVIE_TYPE, ""), page, {
            setShimmer(false)
            if (page == 1) {
                setupList()
            } else {
                adapter.notifyDataSetChanged()
            }
            isLoading = false
            viewModel.updatePage(arguments?.getString(MOVIE_TYPE, ""))
            rvMovieList.visibility = View.VISIBLE
        }, {
            setShimmer(false)
            isLoading = false
            layoutError.visibility = View.VISIBLE
            rvMovieList.visibility = View.GONE
        })

    }

    fun setupList() {
        adapter = GenericAppAdapter(viewModel.popularList)
        val gridLayoutManager = GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false)
        rvMovieList.layoutManager = gridLayoutManager
        rvMovieList.adapter = adapter
        rvMovieList.setHasFixedSize(true)
        setupPagination()
    }

    fun setupPagination() {
        Paginate.with(rvMovieList, this)
            .setLoadingTriggerThreshold(2)
            .addLoadingListItem(true)
            .build()
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

    override fun hasLoadedAllItems(): Boolean =
        viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")) > PAGE_LIMIT

    override fun isLoading(): Boolean = isLoading

    override fun onLoadMore() {
        getData(viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")))
    }
}