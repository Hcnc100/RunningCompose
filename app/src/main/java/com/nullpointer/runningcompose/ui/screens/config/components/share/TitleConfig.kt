package com.nullpointer.runningcompose.ui.screens.config.components.share

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleConfig(
    text: String,
    modifier: Modifier=Modifier
) {
    Text(text = text,
        fontWeight = FontWeight.W500,
        style = MaterialTheme.typography.caption,
        fontSize = 16.sp,
        modifier = modifier.padding(10.dp))
}
