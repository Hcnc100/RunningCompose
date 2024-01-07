package com.nullpointer.runningcompose.inject

import com.nullpointer.runningcompose.datasource.auth.local.AuthLocalDataSource
import com.nullpointer.runningcompose.datasource.auth.local.AuthLocalDataSourceImpl
import com.nullpointer.runningcompose.data.auth.local.AuthDataStore
import com.nullpointer.runningcompose.domain.auth.AuthRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthLocalDatSource(
        authDataStore: AuthDataStore
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(authDataStore)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepoImpl = AuthRepoImpl(authLocalDataSource)
}