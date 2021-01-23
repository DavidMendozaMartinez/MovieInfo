package com.davidmendozamartinez.movieinfo.data.remote

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain

class MovieRemoteDataSource(private val service: TheMovieDBService) {

    suspend fun getPopularMovies(): List<MovieDomain> =
        service.getPopular().results.map { it.toDomain() }

    suspend fun getMovieDetails(id: Int): MovieDomain =
        service.getDetails(id).toDomain()
}