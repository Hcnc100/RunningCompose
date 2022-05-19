package com.nullpointer.runningcompose.ui.screens.config.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun DialogColorPicker(
    hiddenDialog: () -> Unit,
    changeColor: (Color) -> Unit,
) {
    val controller = rememberColorPickerController()
    controller.setWheelColor(Color.DarkGray)
    AlertDialog(onDismissRequest = hiddenDialog,
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
        title = { Text("Selecciona un color", style = MaterialTheme.typography.h6) },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HsvColorPicker(
                    modifier = Modifier
                        .height(250.dp)
                        .wrapContentWidth()
                        .padding(10.dp),
                    controller = controller
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .size(35.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                        shape = RoundedCornerShape(5.dp))
                    .padding(2.dp)
                    .background(controller.selectedColor.value))
            }

        }
    )
}