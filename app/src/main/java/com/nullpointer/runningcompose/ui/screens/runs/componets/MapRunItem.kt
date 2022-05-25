package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.*
import com.nullpointer.runningcompose.models.config.MapConfig

@Composable
fun MapRunItem(
    routeEncode: List<String>,
    mapConfig: MapConfig,
    modifier: Modifier = Modifier,
) {

    val camera= rememberCameraPositionState()

    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false
        )
    }
    val properties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                mapConfig.style.styleRawRes),
        )
    }

    val listPointsDecode = remember {
        routeEncode.map {
            PolyUtil.decode(it)
        }
    }

    val bounds = remember {
        LatLngBounds.builder().let { builder ->
            listPointsDecode.forEach {
                it.forEach(builder::include)
            }
            builder.build()
        }
    }

    GoogleMap(
        cameraPositionState = camera,
        uiSettings = uiSettings,
        modifier = modifier,
        properties = properties,
        googleMapOptionsFactory = {
            GoogleMapOptions().liteMode(true)
        },
        onMapLoaded = {
            camera.move(CameraUpdateFactory.newLatLngBounds(bounds,10))
        }
    ) {
        listPointsDecode.forEach {
            Polyline(
                points = it,
                color = mapConfig.color,
                width = mapConfig.weight.toFloat())
        }
    }
}