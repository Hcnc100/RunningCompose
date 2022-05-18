package com.nullpointer.runningcompose.models.config

import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType

data class MapConfig(
    val style: MapStyle,
    val metrics:MetricType,
    val weight:Int
)