package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.ktx.addPolyline
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.config.MapConfig
import timber.log.Timber


@Composable
fun MapFromConfig(
    mapConfig: MapConfig,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val mapViewState = rememberMapWithLifecycle()
    val listPoints = remember {
        listOf(
            LatLng(53.3477, -6.2597),
            LatLng(51.5008, -0.1224),
            LatLng(48.8567, 2.3508),
            LatLng(52.5166, 13.3833),
        )
    }
    val bounds = remember {
        LatLngBounds.builder().includeAll(listPoints).build()
    }
    AndroidView(
        modifier = modifier
            .height(220.dp)
            .padding(vertical = 10.dp),
        factory = {
            mapViewState.apply {
                // ! this call only one, when map is created, this for config
                getMapAsync {
                    it.uiSettings.setAllGesturesEnabled(false)
                    it.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50))
                }
            }
        },
        update = { mapView ->
            mapView.getMapAsync {
                it.clear()
                it.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(context, mapConfig.style.styleRawRes)
                )
                it.addPolyline {
                    addAll(listPoints)
                    color(mapConfig.colorValue)
                    width(mapConfig.weight.toFloat())
                }
            }
        }
    )
}

fun LatLngBounds.Builder.includeAll(points: List<LatLng>): LatLngBounds.Builder {
    points.forEach(::include)
    return this
}

@Composable
private fun rememberMapWithLifecycle(
    context: Context = LocalContext.current
): MapView {
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }
    val lifecycleObserver = rememberMapLifecycleObserver(mapView = mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(key1 = lifecycleObserver) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

@Composable
private fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver = remember {
    LifecycleEventObserver { _, event ->
        when (event) {
            ON_CREATE -> mapView.onCreate(Bundle())
            ON_START -> mapView.onStart()
            ON_RESUME -> mapView.onResume()
            ON_PAUSE -> mapView.onPause()
            ON_STOP -> mapView.onStop()
            ON_DESTROY -> mapView.onDestroy()
            ON_ANY -> throw IllegalStateException()
        }
    }
}