package com.nullpointer.runningcompose.datasource.run.local

import androidx.paging.PagingSource
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunsLocalDataSource {

    val totalStatisticRuns:Flow<StatisticsRun>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(runEntity: RunData)
    suspend fun insertNewRun(runEntity: RunData)
    suspend fun getListRunsById(listIds: List<Long>):List<RunData>
    fun getListForTypeSort(sortType: SortType, ascendant:Boolean): PagingSource<Int, RunEntity>

    fun getListOrderByDate(limit:Int):Flow<List<RunData>>
    fun getCountRun(): Flow<Int>
}