package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.share.material3.RunningPrimaryButton
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

@Composable
internal fun RunsActionBar(
    trackingState: TrackingState,
    isSelecting: Boolean,
    onStartOrContinue: () -> Unit,
    onDelete: () -> Unit,
) {
    Surface(color = MaterialTheme.colorScheme.surfaceContainerLow) {
        val modifier = Modifier.fillMaxWidth().padding(
            horizontal = RunningSpacing.medium, vertical = RunningSpacing.compact,
        )
        if (isSelecting) {
            Button(
                onClick = onDelete, modifier = modifier.heightIn(min = 48.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ),
            ) { Text(stringResource(R.string.m3_delete_runs)) }
        } else {
            RunningPrimaryButton(
                text = stringResource(if (trackingState == TrackingState.WAITING) R.string.m3_start_run else R.string.m3_continue_run),
                onClick = onStartOrContinue, modifier = modifier,
            )
        }
    }
}
