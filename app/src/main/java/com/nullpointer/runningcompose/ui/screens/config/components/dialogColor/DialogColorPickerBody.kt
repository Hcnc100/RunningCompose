package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews


@Composable
fun DialogColorPickerBody(
    textColor: String,
    controller: ColorPickerController,
    orientation: Int = LocalConfiguration.current.orientation,
) {
    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            DialogColorPickerBodyPortrait(
                controller = controller,
                textColor = textColor,
            )
        }

        else -> {
            DialogColorPickerBodyLandscape(
                controller = controller,
                textColor = textColor
            )
        }
    }
}

@OrientationPreviews
@Composable
private fun DialogColorPickerBodyPreview() {
    DialogColorPickerBody(
        textColor = "#FFFFFF",
        controller = ColorPickerController()
    )
}