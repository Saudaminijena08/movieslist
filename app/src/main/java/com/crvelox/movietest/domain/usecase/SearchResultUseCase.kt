package com.crvelox.movietest.domain.usecase

import com.crvelox.movietest.data.remote.res.SearchResponse
import com.crvelox.movietest.domain.repo.Repository
import com.velox.lazeir.utils.NetworkResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchResultUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        pageNo: Int,
        type: String,
    ): Flow<NetworkResource<SearchResponse>> {
        return repository.getSearchResult(pageNo, type)
    }
}