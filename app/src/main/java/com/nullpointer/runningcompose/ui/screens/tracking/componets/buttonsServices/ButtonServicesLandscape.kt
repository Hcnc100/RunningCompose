package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.preview.config.LandscapePreview
import com.nullpointer.runningcompose.ui.preview.states.TrackingStateProvider
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun ButtonsServicesLandscape(
    servicesState: TrackingState,
    modifier: Modifier = Modifier,
    actionServices: (TrackingActions) -> Unit,
) {
    TrackingControls(servicesState, actionServices, modifier)
}


@LandscapePreview
@Composable
private fun ButtonsServicesLandscapePreview(
    @PreviewParameter(TrackingStateProvider::class) state: TrackingState
) {
    ButtonsServicesLandscape(
        servicesState = state,
        actionServices = { }
    )
}


