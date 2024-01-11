package com.nullpointer.runningcompose.ui.preview.states

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nullpointer.runningcompose.models.types.MetricType

class MetrictTypeProvider : PreviewParameterProvider<MetricType> {
    override val values: Sequence<MetricType> = MetricType.values().asSequence()
}
