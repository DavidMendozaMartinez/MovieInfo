package com.davidmendozamartinez.movieinfo.data.remote

import com.davidmendozamartinez.movieinfo.BuildConfig
import com.davidmendozamartinez.movieinfo.data.remote.model.GetPopularResponse
import com.davidmendozamartinez.movieinfo.data.remote.model.MovieRemote
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

fun createTheMovieDBService(): TheMovieDBService {
    val retrofit = Retrofit.Builder()
        .baseUrl(Routes.THE_MOVIE_DB_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(TheMovieDBService::class.java)
}

interface TheMovieDBService {

    @GET(Routes.GET_POPULAR)
    suspend fun getPopular(
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): GetPopularResponse

    @GET(Routes.GET_DETAILS)
    suspend fun getDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.THE_MOVIE_DB_API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieRemote
}