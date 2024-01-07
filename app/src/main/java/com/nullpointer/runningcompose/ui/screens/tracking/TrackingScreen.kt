package com.nullpointer.runningcompose.ui.screens.tracking

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitSnapshot
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.shareViewModel
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.WAITING
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.TrackingViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions.*
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ButtonsServices
import com.nullpointer.runningcompose.ui.screens.tracking.componets.DialogCancel
import com.nullpointer.runningcompose.ui.screens.tracking.componets.DialogSaved
import com.nullpointer.runningcompose.ui.screens.tracking.componets.MapTracking
import com.nullpointer.runningcompose.ui.share.ToolbarBackWithAction
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch


@Destination(
    deepLinks = [
        DeepLink(uriPattern = "https://www.running-compose.com/tracking")
    ]
)
@MainNavGraph
@Composable
fun TrackingScreen(
    actionRootDestinations: ActionRootDestinations,
    runsViewModel: RunsViewModel = shareViewModel(),
    trackingViewModel: TrackingViewModel = hiltViewModel(),
    trackingState: OrientationScreenState = rememberOrientationScreenState()
) {
    val lastLocation by trackingViewModel.lastLocation.collectAsState()
    val drawPolyData by trackingViewModel.drawLinesData.collectAsState()
    val timeRun by trackingViewModel.timeRun.collectAsState()
    val servicesState by trackingViewModel.stateTracking.collectAsState()
    val mapViewState = rememberMapWithLifecycle()


    Scaffold(
        topBar = {
            ToolbarBackWithAction(title = stringResource(R.string.title_tracking_screen),
                actionBack = actionRootDestinations::backDestination,
                actionCancel = if (servicesState != WAITING) {
                    { trackingViewModel.changeDialogCancel(true) }
                } else null
            )
        },
    ) {

        MapAndTime(
            modifier = Modifier.padding(it),
            orientation = trackingState.orientation,
            drawPolyData = drawPolyData,
            timeRun = timeRun,
            lastLocation = lastLocation,
            servicesState = servicesState,
            mapViewState = mapViewState,
            actionServices = { action ->
                when (action) {
                    START -> TrackingServices.startServicesOrResume(trackingState.context)
                    RESUME -> TrackingServices.pauseServices(trackingState.context)
                    SAVED -> {
                        trackingViewModel.changeDialogSaved(true)
                        trackingState.scope.launch {
                            val map = mapViewState.awaitMap()
                            // * disable animation to last location
                            TrackingServices.pauseServices(trackingState.context)
                            trackingViewModel.changeAnimation(false)
                            val bounds = LatLngBounds.builder().apply {
                                drawPolyData.listLocation.forEach { list ->
                                    list.forEach { latLng ->
                                        include(latLng)
                                    }
                                }
                            }.build()
                            val cameraUpdate =
                                CameraUpdateFactory.newLatLngBounds(bounds, 500, 500, 10)
                            map.moveCamera(cameraUpdate)
                            val bitmapMap = map.awaitSnapshot()
                            runsViewModel.insertNewRun(
                                timeRun = timeRun,
                                listPoints = drawPolyData.listLocation,
                               bitmap = bitmapMap
                            )
                            TrackingServices.finishServices(trackingState.context)
                            actionRootDestinations.backDestination()
                        }
                    }
                }
            }
        )
        Text(text = lastLocation.toString())

        if (trackingViewModel.isShowDialogSave)
            DialogSaved()

        if (trackingViewModel.isShowDialogCancel)
            DialogCancel(
                actionCancel = { trackingViewModel.changeDialogCancel(false) },
                acceptAction = {
                    TrackingServices.finishServices(trackingState.context)
                    actionRootDestinations.backDestination()
                }
            )
    }
}

@Composable
private fun MapAndTime(
    orientation: Int,
    timeRun: Long,
    drawPolyData: DrawPolyData,
    lastLocation: LatLng?,
    servicesState: TrackingState,
    modifier: Modifier = Modifier,
    mapViewState: MapView,
    actionServices: (TrackingActions) -> Unit,
) {

    val textRun = remember(timeRun) {
        timeRun.toFullFormatTime(true)
    }

    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column(modifier = modifier.fillMaxSize()) {
                MapTracking(
                    drawPolyData = drawPolyData,
                    lastLocation = lastLocation,
                    modifier = Modifier.weight(.75f),
                    mapViewState = mapViewState
                )
                ContainerPortraitInfo(
                    modifier = Modifier.weight(.25f)
                ) {
                    TextTimeRun(textRun = textRun)
                    Row {
                        ButtonsServices(
                            actionServices = actionServices,
                            servicesState = servicesState
                        )
                    }
                }
            }
        }
        Configuration.ORIENTATION_LANDSCAPE -> {
            Box(modifier = Modifier.fillMaxSize()) {
                MapTracking(
                    lastLocation = lastLocation,
                    drawPolyData = drawPolyData,
                    modifier = Modifier.fillMaxSize(),
                    mapViewState = mapViewState
                )

                TextTimeRun(
                    textRun = textRun, modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(MaterialTheme.colors.primary)
                        .padding(5.dp)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                ) {
                    ButtonsServices(
                        actionServices = actionServices,
                        servicesState = servicesState
                    )
                }
            }
        }
    }
}

@Composable
private fun TextTimeRun(
    textRun: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = textRun,
        style = MaterialTheme.typography.h4,
        color = Color.White,
        modifier = modifier
    )
}


@Composable
private fun ContainerPortraitInfo(
    modifier: Modifier = Modifier,
    container: @Composable ColumnScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        Column(
            content = container,
            horizontalAlignment = Alignment.CenterHorizontally
        )
    }
}






