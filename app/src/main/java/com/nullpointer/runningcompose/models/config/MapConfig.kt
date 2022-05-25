package com.nullpointer.runningcompose.models.config

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import com.nullpointer.runningcompose.models.types.MapStyle
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapConfig(
    val style: MapStyle,
    val weight: Int,
    val colorValue: Int,
) : Parcelable {
    val color get() = Color(colorValue)
}