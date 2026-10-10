package com.nullpointer.runningcompose.ui.screens.tracking.componets.map

import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.RoundCap
import com.google.android.gms.maps.model.JointType
import com.google.maps.android.ktx.addPolyline
import com.google.maps.android.ktx.awaitMap
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.data.config.MapConfig

internal class TrackingPolylineRenderer {
    val lines = mutableListOf<Polyline>()
    private var config: MapConfig? = null

    fun update(map: GoogleMap, data: DrawPolyData) {
        if (lines.size != data.listLocation.size || config != data.mapConfig) {
            lines.forEach { it.remove() }
            lines.clear()
            config = data.mapConfig
            data.listLocation.forEach { points ->
                lines.add(map.addPolyline {
                    color(data.mapConfig.colorValue)
                    width(data.mapConfig.weight.toFloat())
                    startCap(RoundCap())
                    endCap(RoundCap())
                    jointType(JointType.ROUND)
                    addAll(points)
                })
            }
        } else {
            lines.lastOrNull()?.points = data.listLocation.last()
        }
    }
}

@Composable
fun MapTracking(
    lastLocation: LatLng?,
    mapViewState: MapView,
    drawPolyData: DrawPolyData,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val renderer = remember(mapViewState) { TrackingPolylineRenderer() }

    LaunchedEffect(key1 = lastLocation) {
        lastLocation?.let {
            val map = mapViewState.awaitMap()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 17f))
        }
    }

    LaunchedEffect(key1 = drawPolyData) {
        val map = mapViewState.awaitMap()
        renderer.update(map, drawPolyData)
    }


    AndroidView(
        modifier = modifier,
        factory = {
            mapViewState.apply {
                if (parent != null) {
                    (parent as ViewGroup).removeView(this) // <- fix
                }
            }
        },
        update = { mapView ->
            mapView.getMapAsync { map ->
                map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        context,
                        drawPolyData.mapConfig.style.styleRawRes
                    )
                )
            }
        }
    )
}
