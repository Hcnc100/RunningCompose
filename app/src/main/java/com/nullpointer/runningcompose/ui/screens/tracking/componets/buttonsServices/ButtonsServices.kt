package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.models.types.TrackingState.PAUSE
import com.nullpointer.runningcompose.models.types.TrackingState.TRACKING
import com.nullpointer.runningcompose.models.types.TrackingState.WAITING
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun ButtonsServices(
    actionServices: (TrackingActions) -> Unit,
    servicesState: TrackingState
) {
    Row(
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

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ButtonsServicesPausePreview() {
    ButtonsServices(
        actionServices = {},
        servicesState = PAUSE,
    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ButtonsServicesTrackingPreview() {
    ButtonsServices(
        actionServices = {},
        servicesState = TRACKING,
    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ButtonsServicesWaitingPreview() {
    ButtonsServices(
        actionServices = {},
        servicesState = WAITING,
    )
}
