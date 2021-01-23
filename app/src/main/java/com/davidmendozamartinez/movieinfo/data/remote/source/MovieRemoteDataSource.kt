package com.davidmendozamartinez.movieinfo.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.davidmendozamartinez.movieinfo.data.remote.TheMovieDBService
import com.davidmendozamartinez.movieinfo.data.remote.model.toDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

class MovieRemoteDataSource(private val service: TheMovieDBService) {

    companion object {
        private const val PAGE_SIZE = 20
        private const val MAX_SIZE = 100
    }

    fun getPopularMovies(): Flow<PagingData<MovieDomain>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                maxSize = MAX_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(service) }
        ).flow

    suspend fun getMovieDetails(id: Int): MovieDomain =
        service.getDetails(id).toDomain()
}