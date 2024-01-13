package com.nullpointer.runningcompose.datasource.config.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.nullpointer.runningcompose.data.config.local.ConfigUserStore
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ConfigLocalDataSourceImpl(
    private val configUserStore: ConfigUserStore,
) : ConfigLocalDataSource {

    private suspend fun getSettingsDataFirst() = configUserStore.getSettingsData().first()

    override val mapConfig: Flow<MapConfig> = configUserStore.getSettingsData().map { it.mapConfig }.distinctUntilChanged()
    override val numberRunsGraph: Flow<Int> = configUserStore.getSettingsData().map { it.numberRunsGraph }.distinctUntilChanged()
    override val sortConfig: Flow<SortConfig> = configUserStore.getSettingsData().map { it.sortConfig }.distinctUntilChanged()
    override val metricsConfig: Flow<MetricType> = configUserStore.getSettingsData().map { it.metricConfig }.distinctUntilChanged()
    override val isFirstPermissionLocation: Flow<Boolean> = configUserStore.getSettingsData().map { it.isFirstRequestLocationPermission }.distinctUntilChanged()
    override val isFirstPermissionNotify: Flow<Boolean> =
        configUserStore.getSettingsData().map { it.isFirstRequestNotifyPermission }
            .distinctUntilChanged()
    override val isFirstOpen: Flow<Boolean> =
        configUserStore.getSettingsData().map { it.isFirstOpen }.distinctUntilChanged()
    override suspend fun changeMapConfig(
        style: MapStyle?,
        weight: Int?,
        color: Color?,
    ) {
        val newSettings = getSettingsDataFirst().let { settingsData ->
            with(settingsData.mapConfig) {
                settingsData.copy(
                    mapConfig = MapConfig(
                        style = style ?: this.style,
                        colorValue = color?.toArgb() ?: this.colorValue,
                        weight = weight ?: this.weight
                    )
                )
            }
        }
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?) {
        val newSettings = getSettingsDataFirst().let { settingsData ->
            with(settingsData.sortConfig) {
                settingsData.copy(
                    sortConfig = SortConfig(
                        sortType = sortType ?: this.sortType,
                        isReverse = isReverse ?: this.isReverse
                    )
                )
            }
        }
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeMetricsConfig(metricType: MetricType) {
        val newSettings = getSettingsDataFirst().copy(metricConfig = metricType)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstPermissionLocation() {
        val newSettings = getSettingsDataFirst().copy(isFirstRequestLocationPermission = false)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstPermissionNotify() {
        val newSettings = getSettingsDataFirst().copy(isFirstRequestNotifyPermission = false)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeNumberRunsGraph(numberRunsGraph: Int) {
        val newSettings = getSettingsDataFirst().copy(numberRunsGraph = numberRunsGraph)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstOpen() {
        val newSettings = getSettingsDataFirst().copy(isFirstOpen = false)
        configUserStore.updateSettingsData(newSettings)
    }
}