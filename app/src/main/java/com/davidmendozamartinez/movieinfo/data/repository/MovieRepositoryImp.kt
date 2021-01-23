package com.davidmendozamartinez.movieinfo.data.repository

import com.davidmendozamartinez.movieinfo.data.remote.MovieRemoteDataSource
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import com.davidmendozamartinez.movieinfo.domain.repository.MovieRepository

class MovieRepositoryImp(private val remoteDataSource: MovieRemoteDataSource) : MovieRepository {

    override suspend fun getPopularMovies(): List<MovieDomain> =
        remoteDataSource.getPopularMovies()

    override suspend fun getMovieDetails(id: Int): MovieDomain =
        remoteDataSource.getMovieDetails(id)
}