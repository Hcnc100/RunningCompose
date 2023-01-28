package com.nullpointer.runningcompose.data.local.datasource.runs

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.data.local.room.RunDAO
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.SortType.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class RunsLocalDataSourceImpl(
    private val runDao: RunDAO,
) : RunsLocalDataSource {

    override val totalStatisticRuns = combine(
        runDao.getTotalAVGSpeed(),
        runDao.getTotalCaloriesBurned(),
        runDao.getTotalDistance(),
        runDao.getTotalTimeInMillis()
    ) { speed, calories, distance, time ->
        StatisticsRun(
            distance = distance,
            AVGSpeed = speed ?: 0F,
            timeRun = time,
            caloriesBurned = calories
        )
    }


    override suspend fun insertNewRun(run: Run) =
        runDao.insertNewRun(run)

    override suspend fun getListRunsById(listIds: List<Long>) =
        runDao.getListRunsById(listIds)

    override suspend fun deleterListRuns(listIds: List<Long>) =
        runDao.deleterListRuns(listIds)

    override suspend fun deleterRun(run: Run) =
        runDao.deleterRun(run)

    override fun getListForTypeSort(sortType: SortType, ascendant: Boolean): Flow<List<Run>> =
        runDao.getListRunsBy(sortType,ascendant)
}