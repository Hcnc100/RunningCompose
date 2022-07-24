package com.nullpointer.runningcompose.inject

import com.nullpointer.runningcompose.domain.config.ConfigRepoImpl
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.location.TrackingRepoImpl
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.domain.runs.RunRepoImpl
import com.nullpointer.runningcompose.domain.runs.RunRepository
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
    abstract fun provideConfigRepository(
        configRepoImpl: ConfigRepoImpl
    ):ConfigRepository

    @Binds
    @Singleton
    abstract fun provideRunsRepository(
        runRepoImpl: RunRepoImpl
    ):RunRepository

    @Binds
    @Singleton
    abstract fun provideLocationRepository(
        locationRepoImpl: TrackingRepoImpl
    ):TrackingRepository
}