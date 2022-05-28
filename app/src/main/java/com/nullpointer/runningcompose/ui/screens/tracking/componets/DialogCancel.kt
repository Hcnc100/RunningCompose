package com.nullpointer.runningcompose.ui.screens.tracking.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer

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
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.title_dialog_cancel_tracking), style = MaterialTheme.typography.h6)
                LottieContainer(modifier = Modifier
                    .size(150.dp),
                    animation = R.raw.clear)
                Text(stringResource(R.string.message_info_cancel_tracking),
                    style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(20.dp))
            }
        },
    )

}
