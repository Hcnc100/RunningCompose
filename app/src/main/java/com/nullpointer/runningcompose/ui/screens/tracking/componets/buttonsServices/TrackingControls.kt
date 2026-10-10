package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
internal fun TrackingControls(state: TrackingState, action: (TrackingActions) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { action(if (state == TrackingState.TRACKING) TrackingActions.RESUME else TrackingActions.START) },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
        ) {
            Text(stringResource(when (state) {
                TrackingState.WAITING -> R.string.m3_start_run
                TrackingState.TRACKING -> R.string.m3_pause_run
                TrackingState.PAUSE -> R.string.m3_continue_run
            }))
        }
        if (state != TrackingState.WAITING) {
            OutlinedButton(onClick = { action(TrackingActions.SAVED) }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) {
                Text(stringResource(R.string.m3_save_run))
            }
        }
    }
}
