package com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions

@Composable
fun PauseButton(
    actionServices: (TrackingActions) -> Unit
) {
    FloatingActionButton(
        onClick = { actionServices(TrackingActions.RESUME) }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_pause),
            contentDescription = stringResource(id = R.string.description_button_resume)
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun PauseButtonPreview() {
    PauseButton {}
}