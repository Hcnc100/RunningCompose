package com.nullpointer.runningcompose.datasource.run.local

import androidx.paging.PagingSource
import com.nullpointer.runningcompose.database.RunDAO
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

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


    override suspend fun insertNewRun(runData: RunData) =
        runDao.insertNewRun(RunEntity.fromRunData(runData))

    override suspend fun getListRunsById(listIds: List<Long>) =
        runDao.getListRunsById(listIds).map(RunData::fromRunEntity)

    override suspend fun deleterListRuns(listIds: List<Long>) =
        runDao.deleterListRuns(listIds)

    override suspend fun deleterRun(runData: RunData) =
        runDao.deleterRun(RunEntity.fromRunData(runData))

    override fun getListForTypeSort(
        sortType: SortType,
        ascendant: Boolean
    ): PagingSource<Int, RunEntity> =
        runDao.getListRunsBy(sortType, ascendant)

    override fun getListOrderByDate(limit: Int): Flow<List<RunData>> =
        runDao.getLastRunsOrderByDate(limit).map { list ->
            list.map(RunData::fromRunEntity)
        }

    override fun getCountRun(): Flow<Int> =
        runDao.getRowCount()
}

