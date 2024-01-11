package com.nullpointer.runningcompose.ui.preview.states

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nullpointer.runningcompose.models.types.TrackingState

class TrackingStateProvider : PreviewParameterProvider<TrackingState> {
    override val values: Sequence<TrackingState> = TrackingState.values().asSequence()
}

