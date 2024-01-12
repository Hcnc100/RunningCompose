package com.nullpointer.runningcompose.domain.config

import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.datasource.config.local.ConfigLocalDataSource
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class ConfigRepoImpl(
    private val configLocalDataSource: ConfigLocalDataSource,
) : ConfigRepository {
    override val mapConfig: Flow<MapConfig> = configLocalDataSource.mapConfig
    override val sortConfig: Flow<SortConfig> = configLocalDataSource.sortConfig
    override val metricsConfig: Flow<MetricType> = configLocalDataSource.metricsConfig
    override val isFirstPermissionLocation: Flow<Boolean> = configLocalDataSource.isFirstPermissionLocation
    override val isFirstPermissionNotify: Flow<Boolean> = configLocalDataSource.isFirstPermissionNotify
    override val numberRunsGraph: Flow<Int> = configLocalDataSource.numberRunsGraph
    override val isFirstOpen: Flow<Boolean> = configLocalDataSource.isFirstOpen


    override suspend fun changeMapConfig(
        style: MapStyle?,
        weight: Int?,
        color: Color?,
    ) = configLocalDataSource.changeMapConfig(style, weight, color)

    override suspend fun changeMetricConfig(metricType: MetricType) =
        configLocalDataSource.changeMetricsConfig(metricType)


    override suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?) =
        configLocalDataSource.changeSortConfig(sortType, isReverse)

    override suspend fun changeIsFirstPermissionLocation() =
        configLocalDataSource.changeIsFirstPermissionLocation()

    override suspend fun changeIsFirstPermissionNotify() =
        configLocalDataSource.changeIsFirstPermissionNotify()

    override suspend fun changeNumberRunsGraph(numberRunsGraph: Int) =
        configLocalDataSource.changeNumberRunsGraph(numberRunsGraph)

    override suspend fun changeIsFirstOpen() = configLocalDataSource.changeIsFirstOpen()

}