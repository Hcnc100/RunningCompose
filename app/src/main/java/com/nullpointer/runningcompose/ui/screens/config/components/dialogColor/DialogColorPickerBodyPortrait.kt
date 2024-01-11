package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.PortraitPreview

@Composable
fun DialogColorPickerBodyPortrait(
    controller: ColorPickerController,
    textColor: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.title_select_color),
            style = MaterialTheme.typography.h6
        )
        HsvColorPicker(
            modifier = Modifier
                .size(250.dp)
                .padding(10.dp),
            controller = controller,
            )
        InfoColorSelected(
            textColor = textColor,
            colorValue = controller.selectedColor.value
        )
    }
}

@PortraitPreview
@Composable
private fun DialogColorPickerBodyPortraitPreview() {
    DialogColorPickerBodyPortrait(
        controller = ColorPickerController(),
        textColor = "#FFFFF"
    )
}