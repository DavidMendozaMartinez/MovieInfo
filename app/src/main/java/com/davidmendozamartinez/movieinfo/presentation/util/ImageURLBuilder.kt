package com.davidmendozamartinez.movieinfo.presentation.util

object ImageURLBuilder {
    private const val BASE_URL = "https://image.tmdb.org/t/p/"
    private const val FILE_SIZE = "w500"

    fun from(filePath: String): String = "$BASE_URL$FILE_SIZE$filePath"
}