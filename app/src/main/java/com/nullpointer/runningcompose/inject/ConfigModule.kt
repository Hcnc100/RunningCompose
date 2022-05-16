package com.nullpointer.runningcompose.inject

import android.content.Context
import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSource
import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSourceImpl
import com.nullpointer.runningcompose.domain.config.ConfigRepoImpl
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConfigModule {

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context
    ):ConfigUserStore = ConfigUserStore(context)

    @Provides
    @Singleton
    fun provideConfigLocalDataSource(
        configUserStore: ConfigUserStore
    ):ConfigLocalDataSource= ConfigLocalDataSourceImpl(configUserStore)

    @Provides
    @Singleton
    fun provideConfigRepoImpl(
        configLocalDataSource: ConfigLocalDataSource
    ):ConfigRepoImpl= ConfigRepoImpl(configLocalDataSource)
}