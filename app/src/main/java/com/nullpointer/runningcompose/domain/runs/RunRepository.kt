package com.nullpointer.runningcompose.domain.runs

import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    val totalAVGSpeed: Flow<Float?>
    val totalCaloriesBurden: Flow<Float>
    val totalDistance: Flow<Float>
    val totalTimeRun: Flow<Long>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun getListForTypeSort(sort: SortType): Flow<List<Run>>
    suspend fun insertNewRun(run: Run)
}