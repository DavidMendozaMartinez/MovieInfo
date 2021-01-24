package com.davidmendozamartinez.movieinfo.presentation.util

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.google.android.material.math.MathUtils

fun getTranslucentColor(
    baseOffset: Float,
    baseColor: Int = Color.BLACK,
    baseAlpha: Float = 0.6f
): Int {
    val offset = (baseOffset - (-1f)) / (1f - (-1f)) * (1f - 0f) + 0f
    val alpha = MathUtils.lerp(0f, 255f, offset * baseAlpha).toInt()
    return Color.argb(alpha, baseColor.red, baseColor.green, baseColor.blue)
}