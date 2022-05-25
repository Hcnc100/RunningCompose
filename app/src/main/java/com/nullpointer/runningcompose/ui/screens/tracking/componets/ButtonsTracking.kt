package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.services.TrackingServices

@Composable
fun ButtonPlayTracking() {
    val context = LocalContext.current
    when (TrackingServices.stateServices) {
        TrackingState.WAITING -> FloatingActionButton(onClick = {
            TrackingServices.startServices(context)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "")
        }
        TrackingState.TRACKING -> FloatingActionButton(onClick = {
            TrackingServices.pauseOrResumeServices(context)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "")
        }
        TrackingState.PAUSE -> FloatingActionButton(onClick = {
            TrackingServices.pauseOrResumeServices(context)
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_play),
                contentDescription = "")
        }
    }
}

@Composable
fun ButtonPauseTracking(
    actionSave:()->Unit
) {
    FloatingActionButton(onClick =  actionSave) {
        Icon(painter = painterResource(id = R.drawable.ic_stop),
            contentDescription = "")
    }
}