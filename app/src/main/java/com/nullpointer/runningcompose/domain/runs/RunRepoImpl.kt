package com.nullpointer.runningcompose.domain.runs

import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSource
import com.nullpointer.runningcompose.data.local.datasource.runs.RunsLocalDataSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map


@OptIn(ExperimentalCoroutinesApi::class)
class RunRepoImpl(
    private val runsLocalDataSource: RunsLocalDataSource,
    configLocalDataSource: ConfigLocalDataSource,
) : RunRepository {


    override val listRunsOrdered: Flow<List<Run>> = configLocalDataSource.sortConfig.flatMapLatest {
        runsLocalDataSource.getListForTypeSort(it.sortType)
            .map { list -> if (it.isReverse) list.reversed() else list }
    }

    override val listRunsOrderByDate: Flow<List<Run>> =
        runsLocalDataSource.getListForTypeSort(SortType.DATE)

    override val totalStatisticRuns: Flow<StatisticsRun> =
        runsLocalDataSource.totalStatisticRuns

    override suspend fun deleterListRuns(listIds: List<Long>) =
        runsLocalDataSource.deleterListRuns(listIds)

    override suspend fun deleterRun(run: Run) =
        runsLocalDataSource.deleterRun(run)

    override suspend fun insertNewRun(run: Run) =
        runsLocalDataSource.insertNewRun(run)

}