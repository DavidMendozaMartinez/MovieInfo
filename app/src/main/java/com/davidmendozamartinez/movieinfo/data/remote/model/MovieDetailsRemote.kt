package com.davidmendozamartinez.movieinfo.data.remote.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.google.gson.annotations.SerializedName

data class MovieDetailsRemote(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Float,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
)

data class Genre(
    val id: Int,
    val name: String
)

fun MovieDetailsRemote.toDomain(): MovieDetailsDomain =
    MovieDetailsDomain(
        adult,
        backdropPath,
        budget,
        genres.map { it.name },
        homepage,
        id,
        imdbId,
        originalLanguage,
        originalTitle,
        overview,
        popularity,
        posterPath,
        releaseDate,
        revenue,
        runtime,
        status,
        tagline,
        title,
        video,
        voteAverage,
        voteCount
    )