package com.nullpointer.runningcompose.ui.screens.tracking

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.LocationViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import timber.log.Timber

@Destination
@Composable
fun TrackingScreen(
    navigator: DestinationsNavigator,
    configViewModel: ConfigViewModel,
    locationViewModel: LocationViewModel = hiltViewModel(),
) {
    val configMap by configViewModel.mapConfig.collectAsState()
    val context = LocalContext.current
    var properties by remember { mutableStateOf(MapProperties()) }
    val lastLocation by locationViewModel.lastLocation.collectAsState(initial = null)
    val cameraPositionState = rememberCameraPositionState()
    val listPositions by TrackingServices.showListPont.collectAsState()

    LaunchedEffect(key1 = Unit) {
        TrackingServices.showListPont.collect{
            Timber.d("Lista recibida $it")
        }
    }

    LaunchedEffect(key1 = lastLocation) {
        lastLocation?.let {
            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(it, 20F)))
        }
    }

    LaunchedEffect(key1 = configMap) {
        configMap?.let {
            properties = properties.copy(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                    it.style.styleRawRes)
            )
        }
    }
    Scaffold(
        topBar = {
            ToolbarBack(title = "Seguimiento", actionBack = navigator::popBackStack)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(TrackingServices.isServicesLive){
                    TrackingServices.finishServices(context)
                }else{
                    TrackingServices.initServices(context)
                }
            }) {
                Icon(painterResource(id = R.drawable.ic_clear), contentDescription = "")
            }
        }
    ) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties
        ) {
            if (listPositions.isNotEmpty())
                Polyline(points = listPositions,
                    width = configMap?.weight?.toFloat() ?: 10F,
                    color = configMap?.color ?: Color.Black)
        }
    }
}

