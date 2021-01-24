package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain

data class MovieUI(
    val posterPath: String?,
    val id: Int,
    val title: String,
) {
    val posterUrl: String? get() = posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
}

fun MovieUI.toDomain(): MovieDomain = MovieDomain(posterPath, id, title)
fun MovieDomain.toPresentation(): MovieUI = MovieUI(posterPath, id, title)