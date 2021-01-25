package com.davidmendozamartinez.movieinfo.data.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.data.local.source.MovieLocalDataSource
import com.davidmendozamartinez.movieinfo.data.remote.source.MovieRemoteDataSource
import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override fun getPopularMovies(): Flow<PagingData<MovieDomain>> =
        remoteDataSource.getPopularMovies()

    override fun getTopRatedMovies(): Flow<PagingData<MovieDomain>> =
        remoteDataSource.getTopRatedMovies()

    override fun getUpcomingMovies(): Flow<PagingData<MovieDomain>> =
        remoteDataSource.getUpcomingMovies()

    override fun getFavoriteMovies(): Flow<PagingData<MovieDomain>> =
        localDataSource.getFavoriteMovies()

    override fun searchMovies(query: String): Flow<PagingData<MovieDomain>> =
        remoteDataSource.searchMovies(query)

    override suspend fun addFavoriteMovie(movie: MovieDomain) =
        localDataSource.addFavoriteMovie(movie)

    override suspend fun removeFavoriteMovie(movie: MovieDomain) =
        localDataSource.deleteFavoriteMovie(movie)

    override suspend fun isFavoriteMovie(id: Int): Boolean =
        localDataSource.isFavoriteMovie(id)

    override suspend fun getMovieDetails(id: Int): MovieDetailsDomain =
        remoteDataSource.getMovieDetails(id)
}