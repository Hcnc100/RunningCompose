package com.nullpointer.runningcompose.models.data

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.models.data.config.MapConfig

data class DrawPolyData(
    val listLocation:List<List<LatLng>> = listOf(emptyList()),
    val mapConfig: MapConfig = MapConfig()
)