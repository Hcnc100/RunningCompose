package com.nullpointer.runningcompose.ui.preview.states

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanValuesProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean> = sequenceOf(true, false)
}
