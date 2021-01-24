package com.davidmendozamartinez.movieinfo.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class MovieLocal(
    @PrimaryKey @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("poster_path") val posterPath: String?
)

fun MovieLocal.toDomain(): MovieDomain = MovieDomain(posterPath, id, title)
fun MovieDomain.toLocal(): MovieLocal = MovieLocal(id, title, posterPath)