package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun StopButton(
    actionServices: (TrackingActions) -> Unit
) {
    FloatingActionButton(
        onClick = { actionServices(TrackingActions.SAVED) }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_stop),
            contentDescription = stringResource(id = R.string.description_button_stop_and_save_tracking)
        )
    }
}

@SimplePreview
@Composable
fun StopButtonPreview() {
    StopButton {}
}