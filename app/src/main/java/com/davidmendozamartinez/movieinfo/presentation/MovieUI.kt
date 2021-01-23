package com.davidmendozamartinez.movieinfo.presentation

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain

data class MovieUI(
    val id: Int,
    val title: String,
    val posterUrl: String?
)

fun MovieDomain.toPresentation(): MovieUI {
    println(this)

    return MovieUI(
        id,
        title,
        "https://www.themoviedb.org/t/p/w500$posterPath"
    )
}