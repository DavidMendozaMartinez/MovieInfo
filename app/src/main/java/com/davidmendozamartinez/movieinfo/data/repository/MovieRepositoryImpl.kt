package com.davidmendozamartinez.movieinfo.data.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.data.remote.source.MovieRemoteDataSource
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(private val remoteDataSource: MovieRemoteDataSource) : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<MovieDomain>> =
        remoteDataSource.getPopularMovies()

    override suspend fun getMovieDetails(id: Int): MovieDomain =
        remoteDataSource.getMovieDetails(id)
}