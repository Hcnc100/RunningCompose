package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
import com.nullpointer.runningcompose.models.config.MapConfig

@Composable
fun MapComponent(
    cameraPositionState: CameraPositionState,
    properties: MapProperties,
    listPositions: List<LatLng>,
    modifier: Modifier = Modifier,
    configMap: MapConfig? = null,
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties
    ) {
        if (listPositions.isNotEmpty())
            Polyline(points = listPositions,
                width = configMap?.weight?.toFloat() ?: 10F,
                color = configMap?.color ?: Color.Black)
    }
}