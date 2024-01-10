package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor


import android.content.res.Configuration
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toHex

@Composable
fun DialogColorPicker(
    hiddenDialog: () -> Unit,
    changeColor: (Color) -> Unit,
    orientation: Int = LocalConfiguration.current.orientation,
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
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> {
                    DialogColorPickerBodyPortrait(
                        controller = controller,
                        textColor = selectedColor.toHex(),
                    )
                }

                else -> {
                    DialogColorPickerBodyLandscape(
                        controller = controller,
                        textColor = selectedColor.toHex()
                    )
                }
            }
        },
    )
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
    widthDp = 360,
    heightDp = 450
)
@Composable
private fun DialogColorPickerPortraitPreview() {
    DialogColorPicker(
        orientation = Configuration.ORIENTATION_PORTRAIT,
        changeColor = {},
        hiddenDialog = {}
    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
    widthDp = 720,
    heightDp = 360
)
@Composable
private fun DialogColorPickerLandscapePreview() {
    DialogColorPicker(
        orientation = Configuration.ORIENTATION_LANDSCAPE,
        changeColor = {},
        hiddenDialog = {}
    )
}