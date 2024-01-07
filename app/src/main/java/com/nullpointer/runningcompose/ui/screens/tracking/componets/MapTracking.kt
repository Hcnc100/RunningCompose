package com.nullpointer.runningcompose.ui.screens.tracking.componets

import android.content.Context
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.ktx.addPolyline
import com.google.maps.android.ktx.awaitMap
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import timber.log.Timber

@Composable
fun MapTracking(
    lastLocation: LatLng?,
    mapViewState: MapView,
    drawPolyData: DrawPolyData,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val listPolyline = remember { mutableListOf<Polyline>() }
    var currentConfig by remember { mutableStateOf(MapConfig()) }

    LaunchedEffect(key1 = lastLocation) {
        lastLocation?.let {
            val map = mapViewState.awaitMap()
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 17f))
        }
    }

    LaunchedEffect(key1 = drawPolyData) {
        val map = mapViewState.awaitMap()
        // ! Disparity = clear map
        // ! clear map and re draw all polyline if the style change or ir the list in repo
        // ! is different to the lines draw

        // * this for no draw Unnecessary polyline and only update the last polyline points
        if (listPolyline.size != drawPolyData.listLocation.size || currentConfig != drawPolyData.mapConfig) {
            Timber.d("Disparity: clear map and update styles ")
            currentConfig = drawPolyData.mapConfig
            listPolyline.clear()
            drawPolyData.listLocation.forEach {
                listPolyline.add(
                    map.addPolyline {
                        color(currentConfig.colorValue)
                        width(currentConfig.weight.toFloat())
                        addAll(it)
                    }
                )
            }
        } else {
            // * update the last polyline points
            val lastPolyline = listPolyline.last()
            lastPolyline.points = drawPolyData.listLocation.last()
        }
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
