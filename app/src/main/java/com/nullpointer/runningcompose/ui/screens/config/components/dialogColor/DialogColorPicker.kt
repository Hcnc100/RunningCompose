package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor


import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toHex
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews

@Composable
fun DialogColorPicker(
    hiddenDialog: () -> Unit,
    changeColor: (Color) -> Unit,
) {
    val controller = rememberColorPickerController()
    val selectedColor by controller.selectedColor


    AlertDialog(
        onDismissRequest = hiddenDialog,
        confirmButton = {
            Button(onClick = {
                changeColor(controller.selectedColor.value)
                hiddenDialog()
            }) {
                Text(text = stringResource(R.string.action_accept))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = hiddenDialog) {
                Text(text = stringResource(R.string.action_cancel))
            }
        },
        title = {
            DialogColorPickerBody(
                textColor = selectedColor.toHex(),
                controller = controller
            )
        },
    )
}


@OrientationPreviews
@Composable
fun DialogColorPickerPreview() {
    DialogColorPicker(
        hiddenDialog = {},
        changeColor = {}
    )
}
