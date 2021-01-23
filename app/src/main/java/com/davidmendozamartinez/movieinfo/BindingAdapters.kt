package com.davidmendozamartinez.movieinfo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageFromUrl")
fun ImageView.bindImageFromUrl(imageUrl: String?) = load(imageUrl)