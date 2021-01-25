package com.davidmendozamartinez.movieinfo.data.remote

object Routes {
    const val THE_MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/"

    const val GET_POPULAR = "movie/popular"
    const val GET_TOP_RATED = "movie/top_rated"
    const val GET_UPCOMING = "movie/upcoming"
    const val SEARCH_MOVIES = "search/movie"
    const val GET_DETAILS = "movie/{id}"
}