package com.davidmendozamartinez.movieinfo.presentation.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.google.android.material.bottomappbar.BottomAppBar

fun BottomAppBar.hide() {
    performHide()
    animate().setListener(object : AnimatorListenerAdapter() {
        var isCanceled = false
        override fun onAnimationEnd(animation: Animator?) {
            if (isCanceled) return
            visibility = View.GONE
        }

        override fun onAnimationCancel(animation: Animator?) {
            isCanceled = true
        }
    })
}