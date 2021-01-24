package com.davidmendozamartinez.movieinfo.domain.usecase

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTopRatedMoviesUseCase(private val repository: MovieRepository) {
    fun invoke(): Flow<PagingData<MovieDomain>> = repository.getTopRatedMovies()
}