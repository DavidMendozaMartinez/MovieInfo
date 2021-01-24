package com.davidmendozamartinez.movieinfo.data.remote.source

import androidx.paging.PagingSource
import com.davidmendozamartinez.movieinfo.data.remote.Routes
import com.davidmendozamartinez.movieinfo.data.remote.TheMovieDBService
import com.davidmendozamartinez.movieinfo.data.remote.model.GetMoviesReponse
import com.davidmendozamartinez.movieinfo.data.remote.model.toDomain
import com.davidmendozamartinez.movieinfo.domain.model.MovieDomain
import retrofit2.HttpException
import java.io.IOException

private const val THE_MOVIE_DB_STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val service: TheMovieDBService,
    private val route: String
) : PagingSource<Int, MovieDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomain> {
        val page = params.key ?: THE_MOVIE_DB_STARTING_PAGE_INDEX

        return try {
            val response = when (route) {
                Routes.GET_POPULAR -> service.getPopular(page)
                Routes.GET_TOP_RATED -> service.getTopRated(page)
                Routes.GET_UPCOMING -> service.getUpcoming(page)
                else -> GetMoviesReponse(0, emptyList(), 0, 0)
            }

            val data = response.results.map { it.toDomain() }
            val prevKey = if (page == THE_MOVIE_DB_STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (page == response.totalPages) null else page + 1

            LoadResult.Page(data, prevKey, nextKey)

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}