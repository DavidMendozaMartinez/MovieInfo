package com.davidmendozamartinez.movieinfo.domain.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<MovieDomain>>
    suspend fun getMovieDetails(id: Int): MovieDomain
}