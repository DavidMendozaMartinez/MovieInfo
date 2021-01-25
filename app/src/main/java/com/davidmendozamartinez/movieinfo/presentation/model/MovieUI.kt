package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.presentation.util.ImageURLBuilder

data class MovieUI(
    val posterPath: String?,
    val id: Int,
    val title: String,
) {
    val posterUrl: String? get() = posterPath?.let { ImageURLBuilder.from(it) }
}

fun MovieUI.toDomain(): MovieDomain = MovieDomain(posterPath, id, title)
fun MovieDomain.toPresentation(): MovieUI = MovieUI(posterPath, id, title)