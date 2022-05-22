package com.nullpointer.runningcompose.ui.screens.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.*
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.LocationViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination(
    deepLinks = [
        DeepLink(uriPattern = "https://www.running-compose.com/tracking")
    ]
)
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
    val timeRun by TrackingServices.showTimeInMillis.collectAsState()

    LaunchedEffect(key1 = Unit) {
        TrackingServices.showListPont.collect {
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
    ) {
        Box {

            Column {

                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8f),
                    cameraPositionState = cameraPositionState,
                    properties = properties
                ) {
                    if (listPositions.isNotEmpty())
                        Polyline(points = listPositions,
                            width = configMap?.weight?.toFloat() ?: 10F,
                            color = configMap?.color ?: Color.Black)
                }

                Row(modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp), horizontalArrangement = Arrangement.Center) {
                    when (TrackingServices.stateServices) {
                        WAITING -> FloatingActionButton(onClick = {
                            TrackingServices.startServices(context)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ic_play),
                                contentDescription = "")
                        }
                        TRACKING -> FloatingActionButton(onClick = {
                            TrackingServices.pauseOrResumeServices(context)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = "")
                        }
                        PAUSE -> FloatingActionButton(onClick = {
                            TrackingServices.pauseOrResumeServices(context)
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ic_play),
                                contentDescription = "")
                        }
                    }
                    if (TrackingServices.stateServices != WAITING) {
                        Spacer(modifier = Modifier.width(30.dp))
                        FloatingActionButton(onClick = { TrackingServices.finishServices(context) }) {
                            Icon(painter = painterResource(id = R.drawable.ic_stop),
                                contentDescription = "")
                        }
                    }
                }
            }

            Text(timeRun.toFullFormatTime(true),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colors.primary)
                    .padding(vertical = 5.dp, horizontal = 10.dp))
        }


    }
}

