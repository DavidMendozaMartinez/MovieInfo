package com.davidmendozamartinez.movieinfo.data.remote

import com.google.gson.annotations.SerializedName

class GetPopularResponse(
    val page: Int,
    val results: List<MovieRemote>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)