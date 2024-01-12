package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.PAUSE
import com.nullpointer.runningcompose.models.types.TrackingState.TRACKING
import com.nullpointer.runningcompose.models.types.TrackingState.WAITING
import com.nullpointer.runningcompose.ui.preview.states.TrackingStateProvider
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.share.ToolbarBackWithAction

@Composable
fun ToolbarTracking(
    servicesState: TrackingState,
    actionBack: () -> Unit,
    actionCancel: () -> Unit
) {

    when (servicesState) {
        PAUSE, TRACKING -> ToolbarBackWithAction(
            title = stringResource(R.string.title_tracking_screen),
            actionBack = actionBack,
            actionCancel = actionCancel
        )

        WAITING -> ToolbarBack(
            title = stringResource(R.string.title_tracking_screen),
            actionBack = actionBack
        )
    }
}

@Preview
@Composable
private fun ToolbarTrackingPreview(
    @PreviewParameter(TrackingStateProvider::class) trackingState: TrackingState
) {
    ToolbarTracking(
        servicesState = trackingState,
        actionBack = {},
        actionCancel = {}
    )
}