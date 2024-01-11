package com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogCancel

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews

@Composable
fun DialogCancel(
    acceptAction: () -> Unit,
    actionCancel: () -> Unit,
) {

    AlertDialog(
        onDismissRequest = actionCancel,
        confirmButton = {
            Button(onClick = {
                acceptAction()
                actionCancel()
            }) {
                Text(stringResource(id = R.string.action_accept))
            }
        },
        dismissButton = {
            Button(onClick = actionCancel) {
                Text(stringResource(id = R.string.action_cancel))
            }
        },
        title = { AnimationCancelRun() },
    )
}

@ThemePreviews
@Composable
fun DialogCancelPreview() {
    DialogCancel(
        acceptAction = {},
        actionCancel = {}
    )
}


