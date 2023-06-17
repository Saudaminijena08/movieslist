package com.crvelox.movietest.data

import com.crvelox.movietest.data.remote.res.SearchResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterFace {

    @GET
    suspend fun getSearchResult(
        @Url url: String
    ): Response<SearchResponse>


}

