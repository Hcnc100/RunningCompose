package com.nullpointer.runningcompose.ui.screens.tracking.componets.mapAndTime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.preview.config.SimplePreviewBlack
import com.nullpointer.runningcompose.ui.preview.states.TrackingStateProvider
import com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices.ButtonsServicesPortrait

@Composable
fun TextTimeRun(
    textTimeRun: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = textTimeRun,
        style = MaterialTheme.typography.h4,
        color = Color.White,
        modifier = modifier
    )
}


@Composable
fun TimeRunContainer(
    textTimeRun: String,
    modifier: Modifier = Modifier,
    container: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextTimeRun(textTimeRun = textTimeRun)
        container()
    }
}

@SimplePreviewBlack
@Composable
private fun TimeRunPreview() {
    TextTimeRun(
        textTimeRun = "00:00:00"
    )
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun TimeRunContainerPreview(
    @PreviewParameter(TrackingStateProvider::class) state: TrackingState
) {
    TimeRunContainer(
        textTimeRun = "00:00:00"
    ) {
        ButtonsServicesPortrait(
            actionServices = {},
            servicesState = state
        )
    }
}



