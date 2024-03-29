package com.nullpointer.runningcompose.models.data.config

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.nullpointer.runningcompose.models.types.MapStyle
import kotlinx.serialization.Serializable

@Serializable
data class MapConfig(
    val weight: Int = 5,
    val style: MapStyle = MapStyle.LITE,
    val colorValue: Int = Color.Black.toArgb(),
) {
    val color get() = Color(colorValue)
}