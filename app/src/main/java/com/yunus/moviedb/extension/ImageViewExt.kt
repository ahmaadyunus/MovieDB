package com.yunus.moviedb.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.R


@BindingAdapter("loadImage")
fun ImageView.loadImage(imageSource: String) {
    urCircleImage(imageSource, R.drawable.bg_placeholder)
}

fun ImageView.urCircleImage(imageSource: String, placeholder: Int) {
    Glide.with(context)
        .load(BuildConfig.LOAD_IMAGE_URL.plus(imageSource.filterEmpty()))
        .apply(RequestOptions().centerCrop().placeholder(placeholder))
        .into(this)
}