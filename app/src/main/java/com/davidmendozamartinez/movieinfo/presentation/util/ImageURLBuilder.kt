package com.davidmendozamartinez.movieinfo.presentation.util

object ImageURLBuilder {
    private const val BASE_URL = "https://image.tmdb.org/t/p/"

    enum class Size(val width: String) {
        POSTER("w500"),
        BACKDROP("w1280")
    }

    fun from(filePath: String, size : Size): String = "$BASE_URL${size.width}$filePath"
}