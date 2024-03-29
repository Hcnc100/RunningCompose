package com.nullpointer.runningcompose.datasource.config.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.nullpointer.runningcompose.data.config.local.ConfigUserStore
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SettingsData
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

    override val settingsData: Flow<SettingsData> = configUserStore.getSettingsData()
    override suspend fun changeMapConfig(
        style: MapStyle?,
        weight: Int?,
        color: Color?,
    ) {
        val newSettings = settingsData.first().let { settingsData ->
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

    override suspend fun changeSortConfig(
        sortType: SortType?, isReverse: Boolean?
    ) {
        val newSettings = settingsData.first().let { settingsData ->
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
        val newSettings = settingsData.first().copy(metricConfig = metricType)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstPermissionLocation() {
        val newSettings = settingsData.first().copy(isFirstRequestLocationPermission = false)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstPermissionNotify() {
        val newSettings = settingsData.first().copy(isFirstRequestNotifyPermission = false)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeNumberRunsGraph(numberRunsGraph: Int) {
        val newSettings = settingsData.first().copy(numberRunsGraph = numberRunsGraph)
        configUserStore.updateSettingsData(newSettings)
    }

    override suspend fun changeIsFirstOpen() {
        val newSettings = settingsData.first().copy(isFirstOpen = false)
        configUserStore.updateSettingsData(newSettings)
    }
}