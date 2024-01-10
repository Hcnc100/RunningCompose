package com.nullpointer.runningcompose.ui.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BlockProgress(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.LightGray.copy(alpha = 0.4f))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(
    backgroundColor = 0xFFFFF,
    showBackground = true
)
@Composable
private fun BlockProgressPortraitPreview() {
    BlockProgress()
}

@Preview(
    backgroundColor = 0xFFFFF,
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
@Composable
private fun BlockProgressLandscapePreview() {
    BlockProgress()
}