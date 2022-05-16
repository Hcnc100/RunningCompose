package com.nullpointer.runningcompose.inject

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.datasource.LocalDataSource
import com.nullpointer.runningcompose.data.local.datasource.LocalDataSourceImpl
import com.nullpointer.runningcompose.data.local.room.RunDAO
import com.nullpointer.runningcompose.domain.RunRepoImpl
import com.nullpointer.runningcompose.domain.RunRepository
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
        configUserStore: ConfigUserStore
    ):LocalDataSource=LocalDataSourceImpl(
        runDAO,
        configUserStore
    )

    @Provides
     @Singleton
    fun provideRunRepository(
        localDataSource: LocalDataSource
    ):RunRepoImpl=RunRepoImpl(localDataSource)

}