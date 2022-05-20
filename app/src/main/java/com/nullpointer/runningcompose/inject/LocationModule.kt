package com.nullpointer.runningcompose.inject

import android.content.Context
import com.nullpointer.runningcompose.data.local.datasource.location.LocationDataSource
import com.nullpointer.runningcompose.data.local.datasource.location.LocationDataSourceImpl
import com.nullpointer.runningcompose.data.local.location.SharedLocationManager
import com.nullpointer.runningcompose.domain.location.LocationRepoImpl
import com.nullpointer.runningcompose.domain.location.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocationModule {

    @OptIn(DelicateCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideSharedLocationManager(
        @ApplicationContext context: Context,
    ): SharedLocationManager = SharedLocationManager(context,
        (context.applicationContext as Application).applicationScope)

    @Provides
    @Singleton
    fun provideLocationDataSource(
        sharedLocationManager: SharedLocationManager,
    ): LocationDataSource = LocationDataSourceImpl(sharedLocationManager)

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDataSource: LocationDataSource
    ): LocationRepoImpl = LocationRepoImpl(locationDataSource)

}