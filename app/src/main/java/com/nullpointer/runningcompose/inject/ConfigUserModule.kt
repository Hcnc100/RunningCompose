package com.nullpointer.runningcompose.inject

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.room.RunDAO
import com.nullpointer.runningcompose.data.local.room.RunDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConfigUserModule {
    private const val NAME_DATABASE = "RUN_DATABASE"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): RoomDatabase = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        NAME_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideRunDao(
        runDatabase: RunDatabase,
    ): RunDAO = runDatabase.getRunDao()

    fun providePreferences(
        @ApplicationContext context: Context
    ) = ConfigUserStore(context)
}