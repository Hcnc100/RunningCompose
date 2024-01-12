package com.nullpointer.runningcompose.models.data.config

import com.nullpointer.runningcompose.models.types.MetricType
import kotlinx.serialization.Serializable


@Serializable
data class SettingsData(
    val numberRunsGraph: Int = 10,
    val isFirstOpen: Boolean = true,
    val mapConfig: MapConfig = MapConfig(),
    val sortConfig: SortConfig = SortConfig(),
    val metricConfig: MetricType = MetricType.Meters,
    val isFirstRequestNotifyPermission: Boolean = true,
    val isFirstRequestLocationPermission: Boolean = true
)
