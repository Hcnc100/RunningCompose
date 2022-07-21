package com.nullpointer.runningcompose.domain.config

import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSource
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class ConfigRepoImpl(
    private val configLocalDataSource: ConfigLocalDataSource,
) : ConfigRepository {
    override val userConfig: Flow<UserConfig?> = configLocalDataSource.userConfig
    override val mapConfig: Flow<MapConfig> = configLocalDataSource.mapConfig
    override val sortConfig: Flow<SortConfig> = configLocalDataSource.sortConfig
    override val metricsConfig: Flow<MetricType> = configLocalDataSource.metricsConfig
    override val isFirstPermissionLocation: Flow<Boolean> = configLocalDataSource.isFirstPermissionLocation

    override suspend fun changeUserConfig(userConfig: UserConfig) =
        configLocalDataSource.changeUserConfig(userConfig)


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

}