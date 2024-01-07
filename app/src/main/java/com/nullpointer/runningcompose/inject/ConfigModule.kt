package com.nullpointer.runningcompose.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nullpointer.runningcompose.data.config.local.ConfigUserStore
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSource
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSourceImpl
import com.nullpointer.runningcompose.domain.config.ConfigRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConfigModule {

    @Provides
    @Singleton
    fun providePreferences(
        dataStore: DataStore<Preferences>
    ): ConfigUserStore = ConfigUserStore(dataStore)

    @Provides
    @Singleton
    fun provideConfigLocalDataSource(
        configUserStore: ConfigUserStore
    ): ConfigLocalDataSource = ConfigLocalDataSourceImpl(configUserStore)

    @Provides
    @Singleton
    fun provideConfigRepoImpl(
        configLocalDataSource: ConfigLocalDataSource
    ):ConfigRepoImpl= ConfigRepoImpl(configLocalDataSource)
}