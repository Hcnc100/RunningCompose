package com.nullpointer.runningcompose.ui.share

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import com.nullpointer.runningcompose.core.delegates.PropertySavableString


@Composable
fun EditableTextSavable(
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    isEnabled: Boolean = true,
    singleLine: Boolean = false,
    valueProperty: PropertySavableString,
    shape: Shape = MaterialTheme.shapes.small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    changeValue: (String) -> Unit = valueProperty::changeValue,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Surface {
        Column(modifier = modifier) {
            OutlinedTextField(
                shape = shape,
                enabled = isEnabled,
                singleLine = singleLine,
                onValueChange = changeValue,
                isError = valueProperty.hasError,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                value = valueProperty.currentValue,
                modifier = modifierText.fillMaxWidth(),
                visualTransformation = visualTransformation,
                label = { Text(stringResource(id = valueProperty.label)) },
                placeholder = { Text(stringResource(id = valueProperty.hint)) },
            )
            Row {
                Text(
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(.9f),
                    text = if (valueProperty.hasError) stringResource(id = valueProperty.errorValue) else ""
                )
                Text(
                    text = valueProperty.countLength,
                    style = MaterialTheme.typography.caption,
                    color = if (valueProperty.hasError) MaterialTheme.colors.error else Color.Unspecified
                )
            }
        }
    }
}