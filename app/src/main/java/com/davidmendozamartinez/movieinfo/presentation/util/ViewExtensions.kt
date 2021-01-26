package com.davidmendozamartinez.movieinfo.presentation.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.*
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

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

fun BottomAppBar.show() {
    performShow()
    animate().setListener(object : AnimatorListenerAdapter() {
        var isCanceled = false
        override fun onAnimationEnd(animation: Animator?) {
            if (isCanceled) return
            visibility = View.VISIBLE
        }

        override fun onAnimationCancel(animation: Animator?) {
            isCanceled = true
        }
    })
}

fun <T : View> BottomSheetBehavior<T>.hide() {
    state = BottomSheetBehavior.STATE_HIDDEN
}

fun <T : View> BottomSheetBehavior<T>.show() {
    state = BottomSheetBehavior.STATE_EXPANDED
}

fun <T : View> BottomSheetBehavior<T>.setScrimAnimation(scrim: View) {
    addBottomSheetCallback(object :
        BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            scrim.setBackgroundColor(getTranslucentColor(slideOffset))
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            scrim.isVisible = newState != BottomSheetBehavior.STATE_HIDDEN
        }
    })
}

fun View.setStatusBarPadding() {
    val initial = paddingTop
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updatePadding(top = initial + systemBars.top)
        insets
    }
}

fun View.hideIme() {
    with(context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
        clearFocus()
        hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.showIme() {
    with(context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager) {
        requestFocus()
        showSoftInput(this@showIme, 0)
    }
}