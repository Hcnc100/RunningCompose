package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.ktx.addPolyline
import com.google.maps.android.ktx.awaitMap
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private val listPointsMaps=listOf(
    LatLng(53.3477, -6.2597),
    LatLng(51.5008, -0.1224),
    LatLng(48.8567, 2.3508),
    LatLng(52.5166, 13.3833),
)

@Composable
fun MapFromConfig(
    mapConfig: MapConfig,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val mapViewState = rememberMapWithLifecycle()
    val bounds = remember {
        LatLngBounds.builder().includeAll(listPointsMaps).build()
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
            coroutineScope.launch {
                val map = mapView.awaitMap()
                map.clear()
                map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(context, mapConfig.style.styleRawRes)
                )
                map.addPolyline {
                    addAll(listPointsMaps)
                    color(mapConfig.colorValue)
                    width(mapConfig.weight.toFloat())
                }
            }
        }
    )
}

@SimplePreview
@Composable
fun MapFromConfigPreview() {
    MapFromConfig(
        mapConfig = MapConfig(),
        
    )
}