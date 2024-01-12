package com.nullpointer.runningcompose.models.data.config

import com.nullpointer.runningcompose.models.types.MetricType
import kotlinx.serialization.Serializable


@Serializable
data class SettingsData(
    val mapConfig: MapConfig = MapConfig(),
    val sortConfig: SortConfig= SortConfig(),
    val metricConfig: MetricType = MetricType.Meters,
    val isFirstRequestNotifyPermission:Boolean = true,
    val isFirstRequestLocationPermission:Boolean = true,
    val numberRunsGraph:Int = 10,
)
