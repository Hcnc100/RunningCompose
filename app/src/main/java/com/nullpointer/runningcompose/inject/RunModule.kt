package com.nullpointer.runningcompose.inject

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.datasource.runs.RunsLocalDataSource
import com.nullpointer.runningcompose.data.local.datasource.runs.RunsLocalDataSourceImpl
import com.nullpointer.runningcompose.data.local.room.RunDAO
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
        localDataSource: RunsLocalDataSource
    ): RunRepoImpl = RunRepoImpl(localDataSource)

}