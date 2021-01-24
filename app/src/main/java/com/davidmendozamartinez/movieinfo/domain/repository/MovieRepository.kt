package com.davidmendozamartinez.movieinfo.domain.repository

import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.domain.model.MovieDetailsDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<MovieDomain>>
    fun getFavoriteMovies(): Flow<PagingData<MovieDomain>>
    suspend fun addFavoriteMovie(movie: MovieDomain)
    suspend fun removeFavoriteMovie(movie: MovieDomain)
    suspend fun isFavoriteMovie(id: Int): Boolean
    suspend fun getMovieDetails(id: Int): MovieDetailsDomain
}