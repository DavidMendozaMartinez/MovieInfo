package com.davidmendozamartinez.movieinfo.data.remote

import com.davidmendozamartinez.movieinfo.data.remote.model.MovieDetailsRemote
import com.davidmendozamartinez.movieinfo.data.remote.model.MovieRemote

val samplePopularMovies: List<MovieRemote> = listOf(
    MovieRemote("Popular1", 1, "Popular1"),
    MovieRemote("Popular2", 2, "Popular2"),
    MovieRemote("Popular3", 3, "Popular3")
)

val sampleTopRatedMovies: List<MovieRemote> = listOf(
    MovieRemote("TopRated1", 1, "TopRated1"),
    MovieRemote("TopRated2", 2, "TopRated2"),
    MovieRemote("TopRated3", 3, "TopRated3")
)

val sampleUpcomingMovies: List<MovieRemote> = listOf(
    MovieRemote("Upcoming1", 1, "Upcoming1"),
    MovieRemote("Upcoming2", 2, "Upcoming2"),
    MovieRemote("Upcoming3", 3, "Upcoming3")
)

val sampleFavoriteMovies: List<MovieRemote> = listOf(
    MovieRemote("Favorite1", 1, "Favorite1"),
    MovieRemote("Favorite2", 2, "Favorite2"),
    MovieRemote("Favorite3", 3, "Favorite3")
)

val sampleSearchMovies: List<MovieRemote> = listOf(
    MovieRemote("Search1", 1, "Search1"),
    MovieRemote("Search2", 2, "Search2"),
    MovieRemote("Search3", 3, "Search3")
)

val sampleDetails: MovieDetailsRemote =
    MovieDetailsRemote(
        false,
        "Backdrop",
        0,
        emptyList(),
        "Homepage",
        1,
        "ImdbId",
        "OriginalLanguage",
        "OriginalTitle",
        "Overview",
        0f,
        "Poster",
        "ReleaseDate",
        0,
        0,
        "Status",
        "Tagline",
        "Title",
        false,
        0f,
        0
    )