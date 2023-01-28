package com.nullpointer.runningcompose.domain.runs

import android.content.Context
import android.graphics.Bitmap
import androidx.paging.PagingSource
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunRepository {

    val totalStatisticRuns:Flow<StatisticsRun>
    val listRunsOrderByDate:Flow<List<Run>>
    val countRuns:Flow<Int>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun insertNewRun(run: Run,bitmap: Bitmap?)

    fun getAllListRunOrdered(sortType: SortType,isReverse:Boolean): PagingSource<Int, Run>

}