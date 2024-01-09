package com.nullpointer.runningcompose.models.data.config

import com.nullpointer.runningcompose.models.types.MetricType
import kotlinx.serialization.Serializable


@Serializable
data class SettingsData(
    val sortConfig: SortConfig= SortConfig(),
    val metricConfig: MetricType = MetricType.Meters,
    val mapConfig: MapConfig = MapConfig(),
    val isFirstRequestLocationPermission:Boolean = true,
    val isFirstRequestNotifyPermission:Boolean = true,
)
