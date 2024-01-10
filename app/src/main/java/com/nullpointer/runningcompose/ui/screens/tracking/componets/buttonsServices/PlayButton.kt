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
fun PlayButton(
    actionServices: (TrackingActions) -> Unit
) {
    FloatingActionButton(
        onClick = { actionServices(TrackingActions.START) }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = stringResource(id = R.string.description_button_play_tracking)
        )
    }
}


@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true,
)
@Composable
private fun PlayButtonPreview() {
    PlayButton {}
}
