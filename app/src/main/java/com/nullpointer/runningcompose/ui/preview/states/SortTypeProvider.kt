package com.nullpointer.runningcompose.ui.preview.states

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nullpointer.runningcompose.models.types.SortType

class SortTypeProvider : PreviewParameterProvider<SortType> {
    override val values: Sequence<SortType> = SortType.values().asSequence()
}
