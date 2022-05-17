package com.nullpointer.runningcompose.models.types

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.nullpointer.runningcompose.R

enum class MapStyle(
    @RawRes val styleRawRes: Int,
    @StringRes val string: Int,
) {
    LITE(R.raw.style_map_ultra_light, R.string.name_style_lite),
    ASSASSINS(R.raw.style_map_assassin, R.string.name_style_assassin),
    MIDNIGHT(R.raw.style_map_midnight, R.string.name_style_midnight),
    BLUE_ESSENCE(R.raw.style_map_blue_essence, R.string.name_style_blue),
    PAPER(R.raw.style_map_paper, R.string.name_style_paper),
    GRAY(R.raw.style_map_subtle_gray, R.string.name_style_gray),
    UBER(R.raw.style_map_uber, R.string.name_style_uber)
}