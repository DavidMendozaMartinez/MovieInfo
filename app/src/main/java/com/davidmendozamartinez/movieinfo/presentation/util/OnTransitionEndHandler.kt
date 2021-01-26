package com.davidmendozamartinez.movieinfo.presentation.util

import androidx.transition.Transition

interface OnTransitionEndListener: Transition.TransitionListener {

    override fun onTransitionStart(transition: Transition) {}

    override fun onTransitionCancel(transition: Transition) {}

    override fun onTransitionPause(transition: Transition) {}

    override fun onTransitionResume(transition: Transition) {}

}