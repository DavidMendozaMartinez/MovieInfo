package com.davidmendozamartinez.movieinfo.data.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.data.remote.*
import com.davidmendozamartinez.movieinfo.data.remote.model.toDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository : MovieRepository {
    var shouldReturnError: Boolean = false

    override fun getPopularMovies(): Flow<PagingData<MovieDomain>> =
        if (!shouldReturnError) flowOf(PagingData.from(samplePopularMovies.map { it.toDomain() })) else throw AssertionError()

    override fun getTopRatedMovies(): Flow<PagingData<MovieDomain>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleTopRatedMovies.map { it.toDomain() })) else throw AssertionError()

    override fun getUpcomingMovies(): Flow<PagingData<MovieDomain>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleUpcomingMovies.map { it.toDomain() })) else throw AssertionError()

    override fun getFavoriteMovies(): Flow<PagingData<MovieDomain>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleFavoriteMovies.map { it.toDomain() })) else throw AssertionError()

    override fun searchMovies(query: String): Flow<PagingData<MovieDomain>> =
        if (!shouldReturnError) flowOf(PagingData.from(sampleSearchMovies.map { it.toDomain() })) else throw AssertionError()

    override suspend fun addFavoriteMovie(movie: MovieDomain) {

    }

    override suspend fun removeFavoriteMovie(movie: MovieDomain) {

    }

    override suspend fun isFavoriteMovie(id: Int): Boolean = true

    override suspend fun getMovieDetails(id: Int): MovieDetailsDomain =
        if (!shouldReturnError) sampleDetails.toDomain() else throw AssertionError()

}