package com.nullpointer.runningcompose.ui.share.material3

import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/** Preserve shared M2 state/timeout/action behavior while rendering migrated screens in M3. */
@Composable
fun RunningSnackbarHost(state: SnackbarHostState) {
    androidx.compose.material.SnackbarHost(state) { data ->
        Snackbar(
            action = data.actionLabel?.let { label ->
                { TextButton(onClick = data::performAction) { Text(label) } }
            },
        ) { Text(data.message) }
    }
}
