package com.chaudharynabin6.newapp.di

import com.chaudharynabin6.newapp.data.repository.NewsRepositoryImpl
import com.chaudharynabin6.newapp.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ) : NewsRepository
}