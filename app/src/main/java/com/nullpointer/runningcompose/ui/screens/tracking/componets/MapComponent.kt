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
    listPositions: List<List<LatLng>>,
    modifier: Modifier = Modifier,
    configMap: MapConfig? = null,
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = properties,
        onMapLoaded = {

        }
    ) {
        if (listPositions.isNotEmpty()) {
            listPositions.forEach { list ->
                // ! this is the only way to update the list of points
                // * this is for the list is considerate the same list (although a new point is added)
                // * for is the same object
                if (list.size > 1)
                    Polyline(points = ArrayList(list),
                        width = configMap?.weight?.toFloat() ?: 10F,
                        color = configMap?.color ?: Color.Black)
            }
        }
    }
}