package com.nullpointer.runningcompose.ui.screens.tracking

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.ktx.addPolyline
import com.google.maps.android.ktx.awaitMap
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.shareViewModel
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.*
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.TrackingViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.RootNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions.*
import com.nullpointer.runningcompose.ui.screens.tracking.componets.DialogCancel
import com.nullpointer.runningcompose.ui.share.ToolbarBackWithAction
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber

@Destination(
    deepLinks = [
        DeepLink(uriPattern = "https://www.running-compose.com/tracking")
    ]
)
@RootNavGraph
@Composable
fun TrackingScreen(
    locationViewModel: TrackingViewModel = shareViewModel(),
    runsViewModel: RunsViewModel = hiltViewModel(),
    trackingState: OrientationScreenState = rememberOrientationScreenState(),
    actionRootDestinations: ActionRootDestinations
) {
    val (isShowDialog, changeDialogState) = rememberSaveable { mutableStateOf(false) }
    val lastLocation by locationViewModel.lastLocation.collectAsState()
    val drawPolyData by locationViewModel.drawLinesData.collectAsState()
    val timeRun by locationViewModel.timeRun.collectAsState()
    val servicesState by locationViewModel.stateTracking.collectAsState()


    Scaffold(
        topBar = {
            ToolbarBackWithAction(title = stringResource(R.string.title_tracking_screen),
                actionBack = actionRootDestinations::backDestination,
                actionCancel = if (servicesState != WAITING) {
                    { changeDialogState(true) }
                } else null
            )
        },
    ) {

        MapAndTime(
            orientation = trackingState.orientation,
            drawPolyData = drawPolyData,
            timeRun = timeRun,
            lastLocation = lastLocation,
            servicesState = servicesState,
            actionServices = { action ->
                when (action) {
                    START -> TrackingServices.startServices(trackingState.context)
                    RESUME -> TrackingServices.pauseOrResumeServices(trackingState.context)
                    SAVED -> {

                    }
                }
            }
        )
        Text(text = lastLocation.toString())

        if (isShowDialog)
            DialogCancel(
                actionCancel = { changeDialogState(false) },
                acceptAction = {
                    TrackingServices.finishServices(trackingState.context)
                    actionRootDestinations.backDestination()
                }
            )
    }
}

@Composable
fun MapAndTime(
    orientation: Int,
    timeRun: Long,
    drawPolyData: DrawPolyData,
    lastLocation: LatLng?,
    servicesState: TrackingState,
    modifier: Modifier = Modifier,
    actionServices: (TrackingActions) -> Unit,
) {

    val textRun by derivedStateOf { timeRun.toFullFormatTime(true) }


    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column(modifier = modifier.fillMaxSize()) {
                MapTracking(
                    drawPolyData = drawPolyData,
                    lastLocation = lastLocation,
                    modifier = Modifier.weight(.75f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.25f)
                        .background(MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(textRun, style = MaterialTheme.typography.h4)
                        Row {
                            ButtonsServices(
                                modifierSpacer = modifier.width(20.dp),
                                actionServices = actionServices,
                                servicesState = servicesState
                            )
                        }
                    }

                }
            }
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            Box(modifier = Modifier.fillMaxSize()) {
                MapTracking(
                    lastLocation = lastLocation,
                    drawPolyData = drawPolyData,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    textRun,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(MaterialTheme.colors.primary)
                        .padding(10.dp)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .background(MaterialTheme.colors.primary)
                        .padding(10.dp)
                ) {
                    ButtonsServices(
                        modifierSpacer = modifier.height(20.dp),
                        actionServices = actionServices,
                        servicesState = servicesState
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonsServices(
    modifierSpacer: Modifier,
    actionServices: (TrackingActions) -> Unit,
    servicesState: TrackingState
) {
    if (servicesState == WAITING || servicesState == PAUSE) {
        FloatingActionButton(onClick = { actionServices(START) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = stringResource(id = R.string.description_button_play_tracking)
            )
        }
    }

    if (servicesState == TRACKING) {
        FloatingActionButton(onClick = { actionServices(RESUME) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = stringResource(id = R.string.description_button_resume)
            )
        }
    }

    if (servicesState != WAITING) {
        Spacer(modifier = modifierSpacer)
        FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stop),
                contentDescription = stringResource(id = R.string.description_button_stop_and_save_tracking)
            )
        }
    }
}

@Composable
private fun MapTracking(
    drawPolyData: DrawPolyData,
    modifier: Modifier = Modifier,
    lastLocation: LatLng?,
    context: Context = LocalContext.current
) {
    val mapViewState = rememberMapWithLifecycle()
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
            mapViewState
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




