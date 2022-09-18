package com.nullpointer.runningcompose.models

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.models.config.MapConfig

data class DrawPolyData(
    val listLocation:List<List<LatLng>> = listOf(emptyList()),
    val mapConfig: MapConfig= MapConfig()
)