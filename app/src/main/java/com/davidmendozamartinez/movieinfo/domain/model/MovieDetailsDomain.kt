package com.davidmendozamartinez.movieinfo.domain.model

data class MovieDetailsDomain(
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Long,
    val genres: List<String>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Float,
    val posterPath: String?,
    val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int
)