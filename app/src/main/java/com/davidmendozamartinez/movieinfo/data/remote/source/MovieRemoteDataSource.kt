package com.davidmendozamartinez.movieinfo.data.remote.source

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<MovieDomain>>
    fun getTopRatedMovies(): Flow<PagingData<MovieDomain>>
    fun getUpcomingMovies(): Flow<PagingData<MovieDomain>>
    fun searchMovies(query: String): Flow<PagingData<MovieDomain>>
    suspend fun getMovieDetails(id: Int): MovieDetailsDomain
}