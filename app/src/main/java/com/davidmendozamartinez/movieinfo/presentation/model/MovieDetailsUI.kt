package com.davidmendozamartinez.movieinfo.presentation.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.presentation.util.ImageURLBuilder
import com.davidmendozamartinez.movieinfo.presentation.util.ImageURLBuilder.Size

class MovieDetailsUI(
    val backdropUrl: String?,
    val genres: String,
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    private val releaseYear: String?,
    private val runtime: Int?,
    val tagline: String?,
    val title: String,
    val voteAverage: String,
    val voteCount: String
) {
    private val runtimeText: String?
        get() = runtime?.let { "${runtime.div(60)} h ${runtime.rem(60)} min" }

    val posterUrl: String? get() = posterPath?.let { ImageURLBuilder.from(it, Size.POSTER) }
    val data: String get() = "${releaseYear ?: ""} · ${runtimeText ?: ""}"
}

fun MovieDetailsDomain.toPresentation(): MovieDetailsUI =
    MovieDetailsUI(
        backdropPath?.let { ImageURLBuilder.from(it, Size.BACKDROP) },
        genres.take(2).joinToString(" · "),
        id,
        overview,
        posterPath,
        releaseDate?.take(4),
        runtime,
        tagline,
        title,
        voteAverage.toString(),
        voteCount.toString()
    )