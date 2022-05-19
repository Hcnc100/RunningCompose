package com.nullpointer.runningcompose.ui.screens.tracking

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrackingScreen(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        topBar = {
            ToolbarBack(title = "Seguimiento", actionBack = navigator::popBackStack)
        }
    ) {
        val listPoints = listOf(
            LatLng(53.3477, -6.2597),
            LatLng(51.5008, -0.1224),
            LatLng(48.8567, 2.3508),
            LatLng(52.5166, 13.3833),
        )
        val bounds = LatLngBounds.builder().let {
            listPoints.forEach(it::include)
            it.build()
        }
        val cameraPositionState = rememberCameraPositionState ()
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 10))
            }
        ) {
            Polyline(points = listPoints)
        }
    }
}