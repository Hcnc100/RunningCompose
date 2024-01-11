package com.nullpointer.runningcompose.inject

import android.content.Context
import com.nullpointer.runningcompose.datasource.images.local.ImagesLocalDataSource
import com.nullpointer.runningcompose.datasource.images.local.ImagesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImagesModule {

    @Singleton
    @Provides
    fun provideImageLocalDataSource(
        @ApplicationContext context: Context
    ): ImagesLocalDataSource = ImagesLocalDataSourceImpl(context = context)
}