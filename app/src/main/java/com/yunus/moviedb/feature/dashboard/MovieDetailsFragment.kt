package com.yunus.moviedb.feature.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yunus.moviedb.R
import com.yunus.moviedb.base.BaseFragment
import com.yunus.moviedb.base.Constants.IS_LIKED
import com.yunus.moviedb.base.Constants.MOVIE_ID
import com.yunus.moviedb.databinding.FragmentMovieDetailsBinding
import com.yunus.moviedb.extension.shareStringPrefix
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.btn_share as btnShare
import kotlinx.android.synthetic.main.fragment_movie_details.btn_back as btnBack
import kotlinx.android.synthetic.main.fragment_movie_details.shimmer_movie_details_layout as shimmerLayout
import kotlinx.android.synthetic.main.fragment_movie_details.layout_error as layoutError
import kotlinx.android.synthetic.main.fragment_movie_details.layout_moview_details as layoutMovieDetails



class MovieDetailsFragment: BaseFragment() {
    private val viewModel : MovieDetailsViewModel by sharedViewModel()
    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.movieId = arguments?.getInt(MOVIE_ID)
        viewModel.isFavourited.set(arguments?.getBoolean(IS_LIKED, false))
        shimmerLayout.startShimmer()
        viewModel.getMovieDetail(viewModel.movieId, {
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            layoutMovieDetails.visibility = View.VISIBLE
        },{
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
            layoutMovieDetails.visibility = View.GONE
        })

        btnBack.setOnClickListener {
            activity?.finish()
        }

        btnShare.setOnClickListener {
            onShare()
        }
    }

    fun onShare(){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            viewModel.homePage.get().shareStringPrefix()
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}