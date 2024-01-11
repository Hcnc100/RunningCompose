package com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogSaved

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun DialogSavingRun() {
    AlertDialog(
        onDismissRequest = {},
        buttons = {},
        title = { AnimationSavingRun() },
    )
}


@SimplePreview
@Composable
private fun DialogSavingRunPreview() {
    DialogSavingRun()
}