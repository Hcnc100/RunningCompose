package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.services.TrackingServices


@Composable
fun ButtonStopTracking(
    actionSave:()->Unit
) {
    FloatingActionButton(onClick =  actionSave) {
        Icon(painter = painterResource(id = R.drawable.ic_stop),
            contentDescription = stringResource(R.string.description_button_stop_and_save_tracking))
    }
}