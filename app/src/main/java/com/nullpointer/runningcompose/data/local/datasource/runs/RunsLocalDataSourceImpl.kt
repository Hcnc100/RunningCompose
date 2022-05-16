package com.nullpointer.runningcompose.data.local.datasource.runs

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.room.RunDAO
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.SortType.*
import kotlinx.coroutines.flow.Flow

class RunsLocalDataSourceImpl(
    private val runDao: RunDAO
) : RunsLocalDataSource {
    override val totalAVGSpeed: Flow<Float?> = runDao.getTotalAVGSpeed()
    override val totalCaloriesBurden: Flow<Float> = runDao.getTotalCaloriesBurned()
    override val totalDistance: Flow<Float> = runDao.getTotalDistance()
    override val totalTimeRun: Flow<Long> = runDao.getTotalTimeInMillis()

    override suspend fun insertNewRun(run: Run) =
        runDao.insertNewRun(run)

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