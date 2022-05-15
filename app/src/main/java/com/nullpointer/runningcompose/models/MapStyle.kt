package com.nullpointer.runningcompose.models

import androidx.annotation.RawRes
import com.nullpointer.runningcompose.R

enum class MapStyle(@RawRes val styleRawRes: Int) {
    LITE(R.raw.style_map_ultra_light),
    ASSASSINS(R.raw.style_map_assassin),
    MIDNIGHT(R.raw.style_map_midnight),
    BLUE_ESSENCE(R.raw.style_map_blue_essence),
    PAPER(R.raw.style_map_paper),
    GRAY(R.raw.style_map_subtle_gray),
    UBER(R.raw.style_map_uber)
}