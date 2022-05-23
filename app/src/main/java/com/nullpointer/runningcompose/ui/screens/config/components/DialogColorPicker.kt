package com.nullpointer.runningcompose.ui.screens.config.components


import android.content.res.Configuration
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun DialogColorPicker(
    hiddenDialog: () -> Unit,
    changeColor: (Color) -> Unit,
) {
    val controller = rememberColorPickerController()
    controller.setWheelColor(Color.DarkGray)

    AlertDialog(
        onDismissRequest = hiddenDialog,
        confirmButton = {
            Button(onClick = {
                changeColor(controller.selectedColor.value)
                hiddenDialog()
            }) {
                Text(text = "Aceptar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = hiddenDialog) {
                Text(text = "Cancelar")
            }
        },
        title = { BodySelectColor(controller = controller) },
    )
}

@Composable
private fun BodySelectColor(
    controller: ColorPickerController,
) {
    var textColor by remember { mutableStateOf("") }

    when (LocalConfiguration.current.orientation) {

        Configuration.ORIENTATION_LANDSCAPE -> {
            Column {
                Text("Selecciona un color",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                Row {
                    HsvColorPicker(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(.5f),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            textColor = colorEnvelope.hexCode
                        }
                    )
                    Box(modifier = Modifier.weight(.5f).fillMaxHeight(),
                        contentAlignment = Alignment.Center) {

                        InfoColorSelected(
                            textColor = textColor,
                            colorValue = controller.selectedColor.value)
                    }
                }
            }
        }
        else -> {
            Column {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Selecciona un color", style = MaterialTheme.typography.h6)
                    HsvColorPicker(
                        modifier = Modifier
                            .size(250.dp)
                            .padding(10.dp),
                        controller = controller,
                        onColorChanged = { colorEnvelope: ColorEnvelope ->
                            textColor = colorEnvelope.hexCode
                        }
                    )
                    InfoColorSelected(textColor = textColor,
                        colorValue = controller.selectedColor.value)
                }
            }
        }
    }
}

@Composable
private fun InfoColorSelected(
    modifier: Modifier = Modifier,
    textColor: String,
    colorValue: Color,
) {
    Column(modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "#$textColor",
            style = MaterialTheme.typography.caption,
            color = colorValue)
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier
            .size(35.dp)
            .border(
                width = 2.dp,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                shape = RoundedCornerShape(5.dp))
            .padding(2.dp)
            .background(colorValue))

    }
}