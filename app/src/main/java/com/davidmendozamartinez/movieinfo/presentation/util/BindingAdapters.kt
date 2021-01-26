package com.davidmendozamartinez.movieinfo.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import coil.load
import coil.transform.RoundedCornersTransformation
import com.davidmendozamartinez.movieinfo.R

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(imageUrl: String?) = load(imageUrl)

@BindingAdapter("roundedImageFromUrl")
fun ImageView.bindRoundedImageFromUrl(imageUrl: String?) = load(imageUrl) {
    transformations(RoundedCornersTransformation(resources.getDimension(R.dimen.medium_component_corner_radius)))
}

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?) = visible?.let { isVisible = it }

@BindingAdapter("loadingPlaceholder")
fun <T> View.bindLoadingPlaceholder(data: LiveData<T>) {
    background = if (data.value == null) {
        ContextCompat.getDrawable(context, R.drawable.placeholder)
    } else {
        null
    }
}