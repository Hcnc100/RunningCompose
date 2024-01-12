package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun PageIndicator(
    currentPage: Int,
    numberPages: Int,
    colorCurrent: Color,
    colorOther: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 0 until numberPages) {
            Canvas(
                modifier = Modifier
                    .padding(4.dp)
                    .size(12.dp)
            ) {
                drawCircle(
                    color = if (currentPage == i) colorCurrent else colorOther
                )
            }
        }
    }
}

@SimplePreview
@Composable
private fun PageIndicatorPreview() {
    PageIndicator(
        currentPage = 0,
        numberPages = 3,
        colorCurrent = Color.Cyan,
        colorOther = Color.White,
        modifier = Modifier.background(MaterialTheme.colors.primary)
    )
}