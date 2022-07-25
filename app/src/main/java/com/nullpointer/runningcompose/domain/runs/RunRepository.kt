package com.nullpointer.runningcompose.domain.runs

import android.content.Context
import android.graphics.Bitmap
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunRepository {

    val totalStatisticRuns:Flow<StatisticsRun>
    val listRunsOrdered:Flow<List<Run>>
    val listRunsOrderByDate:Flow<List<Run>>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun insertNewRun(run: Run,bitmap: Bitmap?)
}