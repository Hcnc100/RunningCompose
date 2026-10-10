package com.nullpointer.runningcompose.ui.screens.tracking.componets.mapAndTime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontFamily
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
        style = MaterialTheme.typography.headlineMedium,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurface,
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
            .background(MaterialTheme.colorScheme.surfaceContainer, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
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



