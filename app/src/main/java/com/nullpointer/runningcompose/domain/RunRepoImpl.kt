package com.nullpointer.runningcompose.domain

import com.nullpointer.runningcompose.data.local.datasource.LocalDataSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class RunRepoImpl(
    private val localDataSource: LocalDataSource,
) : RunRepository {
    override val totalAVGSpeed: Flow<Float?> = localDataSource.totalAVGSpeed
    override val totalCaloriesBurden: Flow<Float> = localDataSource.totalCaloriesBurden
    override val totalDistance: Flow<Float> = localDataSource.totalDistance
    override val totalTimeRun: Flow<Long> = localDataSource.totalTimeRun
    override val userConfig: Flow<UserConfig?> = localDataSource.userConfig
    override val mapConfig: Flow<MapConfig> = localDataSource.mapConfig
    override val sortConfig: Flow<SortConfig> = localDataSource.sortConfig

    override suspend fun deleterListRuns(listIds: List<Long>) =
        localDataSource.deleterListRuns(listIds)

    override suspend fun deleterRun(run: Run) =
        localDataSource.deleterRun(run)

    override suspend fun getListForTypeSort(sort: SortType): Flow<List<Run>> =
        localDataSource.getListForTypeSort(sort)
}