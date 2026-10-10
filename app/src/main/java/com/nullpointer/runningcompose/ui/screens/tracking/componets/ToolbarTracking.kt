package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarTracking(
    servicesState: TrackingState,
    actionBack: () -> Unit,
    actionCancel: () -> Unit
) {

    TopAppBar(
        title = { Text(stringResource(R.string.title_tracking_screen)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        navigationIcon = {
            IconButton(onClick = actionBack) {
                Icon(painterResource(R.drawable.ic_arrow_back), stringResource(R.string.description_arrow_back))
            }
        },
        actions = {
            if (servicesState != WAITING) IconButton(onClick = actionCancel) {
                Icon(painterResource(R.drawable.ic_clear), stringResource(R.string.description_cancel_run))
            }
        },
    )
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
