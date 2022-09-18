package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun ButtonsServices(
    actionServices: (TrackingActions) -> Unit,
    servicesState: TrackingState
) {
    if (servicesState == TrackingState.WAITING || servicesState == TrackingState.PAUSE) {
        FloatingActionButton(onClick = { actionServices(TrackingActions.START) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = stringResource(id = R.string.description_button_play_tracking)
            )
        }
    }

    if (servicesState == TrackingState.TRACKING) {
        FloatingActionButton(onClick = { actionServices(TrackingActions.RESUME) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = stringResource(id = R.string.description_button_resume)
            )
        }
    }

    if (servicesState != TrackingState.WAITING) {
        Spacer(modifier = Modifier.size(20.dp))
        FloatingActionButton(onClick = { actionServices(TrackingActions.SAVED) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stop),
                contentDescription = stringResource(id = R.string.description_button_stop_and_save_tracking)
            )
        }
    }
}