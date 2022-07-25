package com.nullpointer.runningcompose.data.local.datasource.runs

import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunsLocalDataSource {

    val totalStatisticRuns:Flow<StatisticsRun>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun insertNewRun(run: Run)
    suspend fun getListRunsById(listIds: List<Long>):List<Run>
    fun getListForTypeSort(sort: SortType): Flow<List<Run>>
}