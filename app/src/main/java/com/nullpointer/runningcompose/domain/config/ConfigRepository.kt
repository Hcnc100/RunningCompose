package com.nullpointer.runningcompose.domain.config

import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    val userConfig: Flow<UserConfig?>
    val mapConfig: Flow<MapConfig>
    val sortConfig: Flow<SortConfig>
    val metricsConfig:Flow<MetricType>
    val isFirstPermissionLocation: Flow<Boolean>

    suspend fun changeUserConfig(userConfig: UserConfig)
    suspend fun changeMapConfig(style: MapStyle?, weight: Int?,color: Color?)
    suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?)
    suspend fun changeMetricConfig(metricType:MetricType)
    suspend fun changeIsFirstPermissionLocation()
}