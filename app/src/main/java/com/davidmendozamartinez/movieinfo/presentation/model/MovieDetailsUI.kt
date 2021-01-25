package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain

class MovieDetailsUI(
    val backdropUrl: String?,
    val budget: Long,
    val genres: String,
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val releaseYear: String?,
    val revenue: Long,
    val runtime: Int?,
    val tagline: String?,
    val title: String,
    val voteAverage: Int,
    val voteCount: String
) {
    private val runtimeText: String?
        get() = runtime?.let { "${runtime.div(60)} h ${runtime.rem(60)} min" }

    val posterUrl: String? get() = posterPath?.let { "https://image.tmdb.org/t/p/w500/$it" }
    val voteAverageText: String get() = "$voteAverage%"
    val data: String get() = "${releaseYear ?: ""} · ${runtimeText ?: ""}"
}

fun MovieDetailsDomain.toPresentation(): MovieDetailsUI =
    MovieDetailsUI(
        "https://image.tmdb.org/t/p/w500/$backdropPath",
        budget,
        genres.joinToString(", "),
        id,
        overview,
        posterPath,
        releaseDate?.take(4),
        revenue,
        runtime,
        tagline,
        title,
        (voteAverage * 10).toInt(),
        voteCount.toString()
    )