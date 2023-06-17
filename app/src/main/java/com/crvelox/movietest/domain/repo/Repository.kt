package com.crvelox.movietest.domain.repo

import com.crvelox.movietest.data.remote.res.SearchResponse
import com.velox.lazeir.utils.NetworkResource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getSearchResult(  pageNo:Int,
                                  type:String,): Flow<NetworkResource<SearchResponse>>
}