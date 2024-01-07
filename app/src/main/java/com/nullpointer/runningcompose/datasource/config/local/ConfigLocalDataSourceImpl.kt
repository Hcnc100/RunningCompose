package com.nullpointer.runningcompose.datasource.config.local

import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.data.config.local.ConfigUserStore
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class ConfigLocalDataSourceImpl(
    private val configUserStore: ConfigUserStore,
) : ConfigLocalDataSource {
    override val mapConfig: Flow<MapConfig> = configUserStore.getMapConfig()
    override val sortConfig: Flow<SortConfig> = configUserStore.getSortConfig()
    override val metricsConfig: Flow<MetricType> = configUserStore.getMetrics()
    override val isFirstPermissionLocation: Flow<Boolean> = configUserStore.isFirstPermission()



    override suspend fun changeMapConfig(
        style: MapStyle?,
        weight: Int?,
        color: Color?,
    ) {
        configUserStore.changeMapConfig(style, weight, color)
    }

    override suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?) {
        configUserStore.changeSortConfig(sortType, isReverse)
    }

    override suspend fun changeMetricsConfig(metricType: MetricType) {
        configUserStore.changeMetrics(metricType)
    }

    override suspend fun changeIsFirstPermissionLocation() {
        configUserStore.changePermission()
    }

}