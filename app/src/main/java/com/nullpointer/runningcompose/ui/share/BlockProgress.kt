package com.nullpointer.runningcompose.ui.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews

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

@OrientationPreviews
@Composable
private fun BlockProgressPortraitPreview() {
    BlockProgress()
}

