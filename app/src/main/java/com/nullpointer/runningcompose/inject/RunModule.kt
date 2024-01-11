package com.nullpointer.runningcompose.inject

import com.nullpointer.runningcompose.database.RunDAO
import com.nullpointer.runningcompose.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSource
import com.nullpointer.runningcompose.datasource.images.local.ImagesLocalDataSource
import com.nullpointer.runningcompose.datasource.run.local.RunsLocalDataSource
import com.nullpointer.runningcompose.datasource.run.local.RunsLocalDataSourceImpl
import com.nullpointer.runningcompose.domain.runs.RunRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RunModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(
        runDAO: RunDAO,
    ): RunsLocalDataSource = RunsLocalDataSourceImpl(runDAO)

    @Provides
    @Singleton
    fun provideRunRepository(
        localDataSource: RunsLocalDataSource,
        authLocalDataSource: AuthLocalDataSource,
        configLocalDataSource: ConfigLocalDataSource,
        imagesLocalDataSource: ImagesLocalDataSource,
    ): RunRepoImpl = RunRepoImpl(
        runsLocalDataSource = localDataSource,
        authLocalDataSource = authLocalDataSource,
        configLocalDataSource = configLocalDataSource,
        imagesLocalDataSource = imagesLocalDataSource
    )

}