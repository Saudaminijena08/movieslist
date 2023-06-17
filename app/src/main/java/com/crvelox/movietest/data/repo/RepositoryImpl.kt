package com.crvelox.movietest.data.repo


import com.crvelox.movietest.data.ApiInterFace
import com.crvelox.movietest.data.remote.res.SearchResponse
import com.crvelox.movietest.domain.repo.Repository
import com.velox.lazeir.utils.NetworkResource
import com.velox.lazeir.utils.handleNetworkResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Field
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiInterFace: ApiInterFace,

    ) : Repository {


    override suspend fun getSearchResult(  pageNo:Int,
                                           type:String,): Flow<NetworkResource<SearchResponse>> {

        val url = "https://www.omdbapi.com/?s=${type}&page=${pageNo}&apikey=60e538a0"
        return apiInterFace.getSearchResult(
            url
        ).handleNetworkResponse()
    }


}