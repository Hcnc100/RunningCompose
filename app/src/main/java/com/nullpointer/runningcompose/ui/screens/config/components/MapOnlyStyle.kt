package com.nullpointer.runningcompose.ui.screens.config.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.nullpointer.runningcompose.models.config.MapConfig

@Composable
fun MapFromConfig(
    mapConfig: MapConfig,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var isMapLoaded by remember {
        mutableStateOf(true)
    }
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false
        )
    }
    var properties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                    mapConfig.style.styleRawRes),
            )
        )
    }
    val listPoints = listOf(
        LatLng(53.3477, -6.2597),
        LatLng(51.5008, -0.1224),
        LatLng(48.8567, 2.3508),
        LatLng(52.5166, 13.3833),
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.5008, -0.1224), 3.5F)
    }
    LaunchedEffect(key1 = mapConfig) {
        properties = properties.copy(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                mapConfig.style.styleRawRes)
        )
    }

    Box(contentAlignment = Alignment.Center) {
        GoogleMap(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            googleMapOptionsFactory = {
                GoogleMapOptions().liteMode(true)
            },
            onMapLoaded = {
                isMapLoaded = false
            }
        ) {
            Polyline(points = listPoints,
                width = mapConfig.weight.toFloat(),
                color = mapConfig.color)
        }
        if (isMapLoaded)
            CircularProgressIndicator()
    }

}