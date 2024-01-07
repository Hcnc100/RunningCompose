package com.nullpointer.runningcompose.datasource.run.local

import androidx.paging.PagingSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunsLocalDataSource {

    val totalStatisticRuns:Flow<StatisticsRun>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun insertNewRun(run: Run)
    suspend fun getListRunsById(listIds: List<Long>):List<Run>
    fun getListForTypeSort(sortType: SortType, ascendant:Boolean): PagingSource<Int, Run>

    fun getListOrderByDate(limit:Int):Flow<List<Run>>
    fun getCountRun(): Flow<Int>
}