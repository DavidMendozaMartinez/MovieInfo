package com.davidmendozamartinez.movieinfo.domain.usecase

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository

class AddFavoriteMovieUseCase(private val repository: MovieRepository) {
    suspend fun invoke(movie: MovieDomain) = repository.addFavoriteMovie(movie)
}