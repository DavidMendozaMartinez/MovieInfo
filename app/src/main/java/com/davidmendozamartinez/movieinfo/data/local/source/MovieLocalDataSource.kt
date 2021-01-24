package com.davidmendozamartinez.movieinfo.data.local.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.data.local.MovieInfoDatabase
import com.davidmendozamartinez.movieinfo.data.local.model.toDomain
import com.davidmendozamartinez.movieinfo.data.local.model.toLocal
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val database: MovieInfoDatabase) {

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 100
    }

    suspend fun addFavoriteMovie(movie: MovieDomain) =
        database.movieDao().addFavorite(movie.toLocal())

    fun getFavoriteMovies(): Flow<PagingData<MovieDomain>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                database.movieDao().getFavorites()
                    .map { it.toDomain() }
                    .asPagingSourceFactory().invoke()
            }
        ).flow

    suspend fun deleteFavoriteMovie(movie: MovieDomain) =
        database.movieDao().deleteFavorite(movie.toLocal())

    suspend fun isFavoriteMovie(id: Int) = database.movieDao().isFavorite(id)
}