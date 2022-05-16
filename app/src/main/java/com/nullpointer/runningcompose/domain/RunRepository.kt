package com.nullpointer.runningcompose.domain

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

    val userConfig: Flow<UserConfig?>
    val mapConfig: Flow<MapConfig>
    val sortConfig: Flow<SortConfig>

    suspend fun deleterListRuns(listIds: List<Long>)
    suspend fun deleterRun(run: Run)
    suspend fun getListForTypeSort(sort: SortType): Flow<List<Run>>
}