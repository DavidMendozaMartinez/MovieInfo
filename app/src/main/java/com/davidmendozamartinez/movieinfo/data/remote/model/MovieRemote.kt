package com.davidmendozamartinez.movieinfo.data.remote.model

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.google.gson.annotations.SerializedName

data class MovieRemote(
    @SerializedName("poster_path") val posterPath: String?,
    val id: Int,
    val title: String
)

fun MovieRemote.toDomain(): MovieDomain = MovieDomain(posterPath, id, title)