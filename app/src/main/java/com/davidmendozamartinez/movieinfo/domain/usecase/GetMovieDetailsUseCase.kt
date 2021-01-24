package com.davidmendozamartinez.movieinfo.domain.usecase

import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository

class GetMovieDetailsUseCase(private val repository: MovieRepository) {
    suspend fun invoke(id: Int): MovieDetailsDomain = repository.getMovieDetails(id)
}