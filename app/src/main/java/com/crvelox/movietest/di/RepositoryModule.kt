package com.crvelox.movietest.di


import com.crvelox.movietest.data.ApiInterFace
import com.crvelox.movietest.data.repo.RepositoryImpl
import com.crvelox.movietest.domain.UseCase
import com.crvelox.movietest.domain.repo.Repository
import com.crvelox.movietest.domain.usecase.SearchResultUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        apiInterFace: ApiInterFace,
    ): Repository {
        return RepositoryImpl(
            apiInterFace = apiInterFace,

            )
    }

    @Provides
    @Singleton
    fun ProvideUseCase(
        repo: Repository
    ): UseCase {
        return UseCase(
            searchResultUseCase = SearchResultUseCase(repo)
        )
    }
}

