package com.nullpointer.runningcompose.ui.screens.tracking

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.presentation.TrackingViewModel
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions.RESUME
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions.SAVED
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions.START
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ToolbarTracking
import com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogCancel.DialogCancel
import com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogSaved.DialogSavingRun
import com.nullpointer.runningcompose.ui.screens.tracking.componets.mapAndTime.MapAndTime
import com.nullpointer.runningcompose.ui.states.TrackingScreenState
import com.nullpointer.runningcompose.ui.states.rememberTrackingScreenState
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
    trackingViewModel: TrackingViewModel = hiltViewModel(),
    trackingState: TrackingScreenState = rememberTrackingScreenState()
) {
    val lastLocation by trackingViewModel.lastLocation.collectAsState()
    val drawPolyData by trackingViewModel.drawLinesData.collectAsState()
    val timeRun by trackingViewModel.timeRun.collectAsState()
    val servicesState by trackingViewModel.stateTracking.collectAsState()
    val mapViewState = rememberMapWithLifecycle()
    var showDialogCancel by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        trackingViewModel.message.collect(trackingState::showSnackMessage)
    }


    Scaffold(
        scaffoldState = trackingState.scaffoldState,
        topBar = {
            ToolbarTracking(
                servicesState = servicesState,
                actionBack = actionRootDestinations::backDestination,
                actionCancel = trackingState::finishTracking
            )
        },
    ) {

        MapAndTime(
            modifier = Modifier.padding(it),
            drawPolyData = drawPolyData,
            timeRun = timeRun,
            lastLocation = lastLocation,
            servicesState = servicesState,
            mapViewState = mapViewState,
            actionServices = { action ->
                when (action) {
                    START -> trackingState.startOrResumeTracking()
                    RESUME -> trackingState.pauseTracking()
                    SAVED -> {
                        trackingState.scope.launch {
                            trackingState.pauseTracking()
                            trackingViewModel.insertNewRun(
                                getMapBitmap = {
                                    trackingState.getSnapshotMap(
                                        mapView = mapViewState,
                                        drawPolyData = drawPolyData
                                    )
                                },
                                onSuccess = {
                                    trackingState.finishTracking()
                                    actionRootDestinations.backDestination()
                                }
                            )
                        }
                    }
                }
            }
        )
        Text(text = lastLocation.toString())

        if (trackingViewModel.isSavingRun) {
            DialogSavingRun()
        }

        if (showDialogCancel) {
            DialogCancel(
                actionCancel = { showDialogCancel = false },
                acceptAction = {
                    TrackingServices.finishServices(trackingState.context)
                    actionRootDestinations.backDestination()
                }
            )
        }
    }
}










