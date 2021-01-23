package com.davidmendozamartinez.movieinfo.domain.repository

import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain

interface MovieRepository {
    suspend fun getPopularMovies(): List<MovieDomain>
    suspend fun getMovieDetails(id: Int): MovieDomain
}