package com.yunus.moviedb.feature.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paginate.Paginate
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.base.Constants.IS_LIKED
import com.yunus.moviedb.base.Constants.MOVIE_ID
import com.yunus.moviedb.base.Constants.MOVIE_TYPE
import com.yunus.moviedb.control.ActivityController
import com.yunus.moviedb.databinding.FragmentMovieListBinding
import com.yunus.moviedb.feature.common.GenericAppAdapter
import com.yunus.moviedb.feature.common.SimpleViewModel
import com.yunus.moviedb.feature.moviedetails.MovieDetailsActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import retrofit2.HttpException
import kotlinx.android.synthetic.main.fragment_movie_list.layout_error as layoutError
import kotlinx.android.synthetic.main.fragment_movie_list.rv_movie_list as rvMovieList
import kotlinx.android.synthetic.main.fragment_movie_list.shimmer_layout as shimmerLayout
import kotlinx.android.synthetic.main.fragment_movie_list.swipe_refresh_layout as swipeRefreshLayout


class MovieListFragment : BaseFragment(), Paginate.Callbacks, SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: GenericAppAdapter<SimpleViewModel>
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.resetPage(arguments?.getString(MOVIE_TYPE, ""))
        getData(viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")))
        swipeRefreshLayout.setOnRefreshListener(this)
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
                viewModel.updatePage(arguments?.getString(MOVIE_TYPE, ""))
            } else {
                adapter.notifyDataSetChanged()
            }
            isLoading = false
            swipeRefreshLayout.isRefreshing = false
            rvMovieList.visibility = View.VISIBLE
        }, {
            if (it is HttpException) {
                if (it.code() == 422) {
                    adapter.notifyDataSetChanged()
                }
            } else {
                setShimmer(false)
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                layoutError.visibility = View.VISIBLE
                rvMovieList.visibility = View.GONE
            }
        },{
            adapter.notifyDataSetChanged()
        }, { id, isLiked ->
            id?.let { it1 ->
                bundle.putInt(MOVIE_ID, it1)
                bundle.putBoolean(IS_LIKED, isLiked)
                ActivityController.navigateToWithBundle(activity, MovieDetailsActivity::class.java, bundle = bundle
                )
            }
        })

    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.resetPage(arguments?.getString(MOVIE_TYPE, ""))
        getData(viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")))
    }

    fun setupList() {
        adapter = GenericAppAdapter(viewModel.getList(arguments?.getString(MOVIE_TYPE, "")))
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

    override fun hasLoadedAllItems(): Boolean = viewModel.isLastPage(arguments?.getString(MOVIE_TYPE, ""))

    override fun isLoading(): Boolean = isLoading

    override fun onLoadMore() {
        getData(viewModel.getPage(arguments?.getString(MOVIE_TYPE, "")))
        viewModel.updatePage(arguments?.getString(MOVIE_TYPE, ""))
    }
}