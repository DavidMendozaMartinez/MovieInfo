package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.R

enum class Section(private val menuItemId: Int, val stringResId: Int) {
    POPULAR(R.id.popular, R.string.nav_drawer_popular),
    TOP_RATED(R.id.top_rated, R.string.nav_drawer_top_rated),
    UPCOMING(R.id.upcoming, R.string.nav_drawer_upcoming),
    FAVORITES(R.id.favorites, R.string.nav_drawer_favorites);

    companion object {
        fun parse(menuItemId: Int): Section = values().first { it.menuItemId == menuItemId }
    }
}