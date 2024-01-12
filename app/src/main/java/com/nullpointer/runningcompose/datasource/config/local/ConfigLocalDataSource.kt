package com.nullpointer.runningcompose.datasource.config.local

import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface ConfigLocalDataSource {
    val mapConfig: Flow<MapConfig>
    val numberRunsGraph:Flow<Int>
    val sortConfig: Flow<SortConfig>
    val metricsConfig:Flow<MetricType>
    val isFirstPermissionLocation: Flow<Boolean>
    val isFirstPermissionNotify: Flow<Boolean>
    val isFirstOpen: Flow<Boolean>

    suspend fun changeMapConfig(style: MapStyle?, weight: Int?, color: Color?)
    suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?)
    suspend fun changeMetricsConfig(metricType: MetricType)
    suspend fun changeIsFirstPermissionLocation()
    suspend fun changeIsFirstPermissionNotify()
    suspend fun changeNumberRunsGraph(numberRunsGraph: Int)
    suspend fun changeIsFirstOpen()
}