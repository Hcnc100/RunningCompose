package com.nullpointer.runningcompose.ui.screens.tracking

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.types.TrackingState.*
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.LocationViewModel
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ButtonPauseTracking
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ButtonPlayTracking
import com.nullpointer.runningcompose.ui.screens.tracking.componets.DialogCancel
import com.nullpointer.runningcompose.ui.screens.tracking.componets.MapComponent
import com.nullpointer.runningcompose.ui.share.ToolbarBackWithAction
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
    runsViewModel: RunsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val configMap by configViewModel.mapConfig.collectAsState()
    var properties by remember { mutableStateOf(MapProperties()) }
    val lastLocation by locationViewModel.lastLocation.collectAsState(initial = null)
    val cameraPositionState = rememberCameraPositionState()
    val (isShowDialog, changeDialogState) = rememberSaveable { mutableStateOf(false) }
    var listPoints by remember { mutableStateOf<List<List<LatLng>>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        TrackingServices.showCounterPoint.collect {
            listPoints = TrackingServices.showListPoints
            Timber.e("actualizacion recibida $it")
            Timber.d("list points $listPoints")
        }
    }

    LaunchedEffect(key1 = lastLocation) {
        lastLocation?.let {
            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(it, 17F)))
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
            ToolbarBackWithAction(title = "Seguimiento",
                actionBack = navigator::popBackStack,
                actionCancel = if (TrackingServices.stateServices != WAITING) {
                    { changeDialogState(true) }
                } else null)
        },
    ) {

        MapAndTimeComponent(
            cameraPositionState = cameraPositionState,
            properties = properties,
            mapConfig = configMap,
            listPoints = listPoints,
            actionSaveRun = {
                TrackingServices.pauseOrResumeServices(context)
                runsViewModel.insertNewRun(
                    timeRun = TrackingServices.showTimeInMillis.value,
                    listPoints = TrackingServices.showListPoints
                )
                TrackingServices.finishServices(context)
                navigator.popBackStack()
            }
        )

        if (isShowDialog)
            DialogCancel(
                actionCancel = { changeDialogState(false) },
                acceptAction = {
                    TrackingServices.finishServices(context)
                    navigator.popBackStack()
                }
            )
    }
}

@Composable
fun MapAndTimeComponent(
    cameraPositionState: CameraPositionState,
    properties: MapProperties,
    mapConfig: MapConfig?,
    listPoints: List<List<LatLng>>,
    actionSaveRun: () -> Unit,
) {
    val timeRun by TrackingServices.showTimeInMillis.collectAsState()

    when (LocalConfiguration.current.orientation) {

        Configuration.ORIENTATION_LANDSCAPE -> {
            Box {
                MapComponent(cameraPositionState = cameraPositionState,
                    properties = properties,
                    listPositions = listPoints,
                    configMap = mapConfig,
                    modifier = Modifier.fillMaxSize())

                Text(text = timeRun.toFullFormatTime(true),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .align(Alignment.TopCenter)
                )

                Column(modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterStart)) {
                    ButtonPlayTracking()
                    if (TrackingServices.stateServices != WAITING) {
                        Spacer(modifier = Modifier.height(30.dp))
                        ButtonPauseTracking(actionSave = actionSaveRun)
                    }
                }
            }
        }
        else -> {
            Column {
                MapComponent(
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    listPositions = listPoints,
                    configMap = mapConfig,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.7f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(20.dp),
                ) {
                    Text(timeRun.toFullFormatTime(true),
                        style = MaterialTheme.typography.h3)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        ButtonPlayTracking()
                        if (TrackingServices.stateServices != WAITING) {
                            Spacer(modifier = Modifier.width(30.dp))
                            ButtonPauseTracking(actionSave = actionSaveRun)
                        }
                    }
                }
            }
        }
    }
}

