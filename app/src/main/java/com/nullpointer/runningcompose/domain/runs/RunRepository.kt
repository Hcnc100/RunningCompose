package com.nullpointer.runningcompose.domain.runs

import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunRepository {

    val totalStatisticRuns:Flow<StatisticsRun>
    val listRunsOrderByDate:Flow<List<RunData>>
    val countRuns:Flow<Int>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(runData: RunData)
    suspend fun insertNewRun(runData: RunData, bitmap: Bitmap?)

    fun getAllListRunOrdered(sortType: SortType,isReverse:Boolean): PagingSource<Int, RunEntity>

}