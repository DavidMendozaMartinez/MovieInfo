package com.davidmendozamartinez.movieinfo.domain.usecase

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    suspend fun invoke(): List<MovieDomain> = repository.getPopularMovies()
}