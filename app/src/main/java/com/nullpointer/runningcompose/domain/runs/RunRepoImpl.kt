package com.nullpointer.runningcompose.domain.runs

import com.nullpointer.runningcompose.data.local.datasource.runs.RunsLocalDataSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class RunRepoImpl(
    private val runsLocalDataSource: RunsLocalDataSource,
) : RunRepository {

    override val totalAVGSpeed: Flow<Float?> = runsLocalDataSource.totalAVGSpeed
    override val totalCaloriesBurden: Flow<Float> = runsLocalDataSource.totalCaloriesBurden
    override val totalDistance: Flow<Float> = runsLocalDataSource.totalDistance
    override val totalTimeRun: Flow<Long> = runsLocalDataSource.totalTimeRun


    override suspend fun deleterListRuns(listIds: List<Long>) =
        runsLocalDataSource.deleterListRuns(listIds)

    override suspend fun deleterRun(run: Run) =
        runsLocalDataSource.deleterRun(run)

    override suspend fun getListForTypeSort(sort: SortType): Flow<List<Run>> =
        runsLocalDataSource.getListForTypeSort(sort)
}