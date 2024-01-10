package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.nullpointer.runningcompose.R

@Composable
fun DialogColorPickerBodyLandscape(
    textColor: String,
    controller: ColorPickerController,
) {
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(R.string.title_select_color),
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row {
            HsvColorPicker(
                modifier = Modifier.weight(0.5F),
                controller = controller,
            )
            Box(
                modifier = Modifier
                    .weight(0.5F)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {

                InfoColorSelected(
                    textColor = textColor,
                    colorValue = controller.selectedColor.value
                )
            }
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    heightDp = 360,
    widthDp = 720
)
@Composable
private fun DialogColorPickerBodyLandscapePreview() {
    DialogColorPickerBodyLandscape(
        textColor = "FFFFF",
        controller = ColorPickerController()
    )
}