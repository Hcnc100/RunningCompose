package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.PAUSE
import com.nullpointer.runningcompose.models.types.TrackingState.TRACKING
import com.nullpointer.runningcompose.models.types.TrackingState.WAITING
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.preview.states.TrackingStateProvider
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun ButtonsServicesPortrait(
    modifier: Modifier = Modifier,
    actionServices: (TrackingActions) -> Unit,
    servicesState: TrackingState,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        when (servicesState) {
            WAITING, PAUSE -> PlayButton(actionServices)
            TRACKING -> PauseButton(actionServices)
        }
        if (servicesState != WAITING) {
            StopButton(actionServices)
        }
    }
}

@SimplePreview
@Composable
private fun ButtonsServicesPortraitPreview(
    @PreviewParameter(TrackingStateProvider::class) state: TrackingState
) {
    ButtonsServicesPortrait(
        actionServices = {},
        servicesState = state,
    )
}

