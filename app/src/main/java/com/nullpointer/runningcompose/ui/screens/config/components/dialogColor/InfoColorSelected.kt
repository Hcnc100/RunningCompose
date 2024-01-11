package com.nullpointer.runningcompose.ui.screens.config.components.dialogColor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews

@Composable
fun InfoColorSelected(
    colorValue: Color,
    textColor: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = textColor,
            style = MaterialTheme.typography.caption,
            color = colorValue
        )
        Box(
            modifier = Modifier
                .size(35.dp)
                .border(
                    width = 2.dp,
                    color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(2.dp)
                .background(colorValue)
        )

    }
}

@ThemePreviews
@Composable
private fun InfoColorSelectedBlackPreview() {
    InfoColorSelected(
        colorValue = Color.Cyan,
        textColor = "FFFFFF",
    )
}
