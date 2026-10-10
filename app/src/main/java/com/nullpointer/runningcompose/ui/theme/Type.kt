package com.nullpointer.runningcompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val RunningTypography = androidx.compose.material3.Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    )
)

val Typography = androidx.compose.material.Typography(
    h1 = RunningTypography.displayLarge,
    h2 = RunningTypography.displayMedium,
    h3 = RunningTypography.displaySmall,
    h4 = RunningTypography.headlineLarge,
    h5 = RunningTypography.headlineSmall,
    h6 = RunningTypography.titleLarge,
    subtitle1 = RunningTypography.titleMedium,
    subtitle2 = RunningTypography.titleSmall,
    body1 = RunningTypography.bodyLarge,
    body2 = RunningTypography.bodyMedium,
    button = RunningTypography.labelLarge,
    caption = RunningTypography.bodySmall,
    overline = RunningTypography.labelSmall,
)
