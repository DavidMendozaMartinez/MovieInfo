package com.davidmendozamartinez.movieinfo.domain.usecase

import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository

class IsFavoriteMovieUseCase(private val repository: MovieRepository) {
    suspend fun invoke(movieId: Int) = repository.isFavoriteMovie(movieId)
}