package com.yunus.moviedb.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yunus.moviedb.BuildConfig
import com.yunus.moviedb.R


@BindingAdapter("loadImage")
fun ImageView.loadImage(imageSource: String) {
        urlImg(imageSource, R.drawable.bg_placeholder)
}

fun ImageView.urlImg(imageSource: String, placeholder: Int) {
    Glide.with(context)
        .load(BuildConfig.LOAD_IMAGE_URL.plus(imageSource.filterEmpty()))
        .apply(RequestOptions().centerCrop().placeholder(placeholder))
        .into(this)
}

@BindingAdapter("liked")
fun ImageView.liked(isLiked: Boolean) {
    if(isLiked){
        loadResourceImage(R.drawable.ic_like_active, R.drawable.bg_placeholder)
    } else {
        loadResourceImage(R.drawable.ic_like_inactive, R.drawable.bg_placeholder)
    }
}

fun ImageView.loadResourceImage(imageId: Int, placeholder: Int) {
    Glide.with(context)
        .load(imageId)
        .apply(RequestOptions().centerCrop().placeholder(placeholder))
        .into(this)
}
