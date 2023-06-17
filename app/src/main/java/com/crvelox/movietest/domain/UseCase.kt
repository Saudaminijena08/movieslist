package com.crvelox.movietest.domain

import com.crvelox.movietest.domain.usecase.SearchResultUseCase


data class UseCase constructor(
    val searchResultUseCase: SearchResultUseCase,
)