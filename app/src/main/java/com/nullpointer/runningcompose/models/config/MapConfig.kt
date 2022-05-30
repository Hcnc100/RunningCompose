package com.nullpointer.runningcompose.models.config

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.nullpointer.runningcompose.models.types.MapStyle
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapConfig(
    val style: MapStyle = MapStyle.LITE,
    val weight: Int = 5,
    val colorValue: Int = Color.Black.toArgb(),
) : Parcelable {
    val color get() = Color(colorValue)
}