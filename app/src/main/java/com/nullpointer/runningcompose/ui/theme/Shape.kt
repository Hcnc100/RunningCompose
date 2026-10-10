package com.nullpointer.runningcompose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val RunningShapes = androidx.compose.material3.Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

// Compatibility names retained while existing screens migrate.
val Shapes = androidx.compose.material.Shapes(
    small = RunningShapes.small,
    medium = RunningShapes.medium,
    large = RunningShapes.extraLarge,
)
