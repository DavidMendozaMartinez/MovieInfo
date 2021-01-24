package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.R

enum class Section(private val menuItemId: Int) {
    POPULAR(R.id.popular),
    TOP_RATED(R.id.top_rated),
    LATEST(R.id.latest),
    FAVORITES(R.id.favorites);

    companion object {
        fun parse(menuItemId: Int): Section = values().first { it.menuItemId == menuItemId }
    }
}