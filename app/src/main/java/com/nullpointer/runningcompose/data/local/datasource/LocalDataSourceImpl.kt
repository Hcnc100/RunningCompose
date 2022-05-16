package com.nullpointer.runningcompose.data.local.datasource

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.room.RunDAO
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.SortType.*
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val runDao: RunDAO,
    private val configUserStore: ConfigUserStore,
) : LocalDataSource {
    override val totalAVGSpeed: Flow<Float?> = runDao.getTotalAVGSpeed()
    override val totalCaloriesBurden: Flow<Float> = runDao.getTotalCaloriesBurned()
    override val totalDistance: Flow<Float> = runDao.getTotalDistance()
    override val totalTimeRun: Flow<Long> = runDao.getTotalTimeInMillis()

    override val userConfig: Flow<UserConfig?> = configUserStore.getUserConfig()
    override val mapConfig: Flow<MapConfig> = configUserStore.getMapConfig()
    override val sortConfig: Flow<SortConfig> = configUserStore.getSortConfig()

    override suspend fun deleterListRuns(listIds: List<Long>) =
        runDao.deleterListRuns(listIds)

    override suspend fun deleterRun(run: Run) =
        runDao.deleterRun(run)

    override suspend fun getListForTypeSort(sort: SortType): Flow<List<Run>> =
        when (sort) {
            DATE -> runDao.getListRunsByTimestamp()
            RUNNING_TIME -> runDao.getListRunsByRunTimeRun()
            AVG_SPEED -> runDao.getListRunsByAvgSpeed()
            DISTANCE -> runDao.getListRunsByDistance()
            CALORIES_BURNED -> runDao.getListRunsByCaloriesBurned()
        }

}