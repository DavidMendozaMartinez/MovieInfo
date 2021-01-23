package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain

data class MovieUI(
    val id: Int,
    val title: String,
    val posterUrl: String?
)

fun MovieDomain.toPresentation(): MovieUI = MovieUI(
    id,
    title,
    "https://www.themoviedb.org/t/p/w500$posterPath"
)