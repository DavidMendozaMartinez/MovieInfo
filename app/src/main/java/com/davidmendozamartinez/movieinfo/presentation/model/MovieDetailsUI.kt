package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain

class MovieDetailsUI(
    val backdropUrl: String?,
    val budget: Long,
    val genres: String,
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val data: String?,
    val revenue: Long,
    val tagline: String?,
    val title: String,
    val voteAverage: String,
    val voteCount: String
){
    val posterUrl: String? get() = posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
}

fun MovieDetailsDomain.toPresentation(): MovieDetailsUI =
    MovieDetailsUI(
        "https://image.tmdb.org/t/p/w500/$backdropPath",
        budget,
        genres.joinToString(", "),
        id,
        overview,
        posterPath,
        "${releaseDate?.take(4) ?: ""} · ${runtime ?: ""}",
        revenue,
        tagline,
        title,
        "${voteAverage * 10}%",
        voteCount.toString()
    )