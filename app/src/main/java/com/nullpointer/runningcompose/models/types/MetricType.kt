package com.nullpointer.runningcompose.models.types

import androidx.annotation.StringRes
import com.nullpointer.runningcompose.R

enum class MetricType(
    @StringRes val stringRes:Int
) {
    Meters(R.string.meters),
    Kilo(R.string.kilo)
}