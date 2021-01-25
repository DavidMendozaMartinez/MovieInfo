package com.davidmendozamartinez.movieinfo.domain.usecase

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(private val repository: MovieRepository) {
    fun invoke(query: String): Flow<PagingData<MovieDomain>> = repository.searchMovies(query)
}