package com.davidmendozamartinez.movieinfo.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(imageUrl: String?) = load(imageUrl)

@BindingAdapter("visible")
fun View.bindVisible(visible: Boolean?) = visible?.let { isVisible = it }