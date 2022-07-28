package com.nullpointer.runningcompose.ui.screens.tracking

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.models.config.MapConfig

data class DrawPolyData(
    val listLocation:List<List<LatLng>> = listOf(emptyList()),
    val mapConfig: MapConfig= MapConfig()
)