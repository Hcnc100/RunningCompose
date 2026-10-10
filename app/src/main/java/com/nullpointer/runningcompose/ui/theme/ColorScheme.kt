package com.nullpointer.runningcompose.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Explicit semantic roles prevent unrelated default accents leaking into the UI.
internal val RunningLightColorScheme = lightColorScheme(
    primary = Purple, onPrimary = Color.White,
    primaryContainer = Color(0xFFE9DDFF), onPrimaryContainer = Color(0xFF23005C),
    inversePrimary = Color(0xFFCFBCFF),
    secondary = Color(0xFF625B71), onSecondary = Color.White,
    secondaryContainer = Color(0xFFE8DEF8), onSecondaryContainer = Color(0xFF1E192B),
    tertiary = Color(0xFF006A60), onTertiary = Color.White,
    tertiaryContainer = Color(0xFF9EF2E3), onTertiaryContainer = Color(0xFF00201C),
    background = Color(0xFFFCFAFF), onBackground = Color(0xFF1D1B20),
    surface = Color(0xFFFCFAFF), onSurface = Color(0xFF1D1B20),
    surfaceVariant = Color(0xFFE7E0EC), onSurfaceVariant = Color(0xFF49454F),
    surfaceTint = Purple,
    inverseSurface = Color(0xFF322F35), inverseOnSurface = Color(0xFFF5EFF7),
    error = Color(0xFFBA1A1A), onError = Color.White,
    errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF410002),
    outline = Color(0xFF7A757F), outlineVariant = Color(0xFFCAC4D0), scrim = Color.Black,
    surfaceBright = Color(0xFFFCFAFF), surfaceDim = Color(0xFFDED8E1),
    surfaceContainerLowest = Color.White, surfaceContainerLow = Color(0xFFF8F2FA),
    surfaceContainer = Color(0xFFF2ECF4), surfaceContainerHigh = Color(0xFFECE6EE),
    surfaceContainerHighest = Color(0xFFE6E0E9),
)

internal val RunningDarkColorScheme = darkColorScheme(
    primary = Color(0xFFCFBCFF), onPrimary = Color(0xFF381E72),
    primaryContainer = Color(0xFF50358A), onPrimaryContainer = Color(0xFFE9DDFF),
    inversePrimary = Purple,
    secondary = Color(0xFFCCC2DC), onSecondary = Color(0xFF332D41),
    secondaryContainer = Color(0xFF4A4458), onSecondaryContainer = Color(0xFFE8DEF8),
    tertiary = Color(0xFF82D5C7), onTertiary = Color(0xFF00372F),
    tertiaryContainer = Color(0xFF005047), onTertiaryContainer = Color(0xFF9EF2E3),
    background = Color(0xFF141218), onBackground = Color(0xFFE6E0E9),
    surface = Color(0xFF141218), onSurface = Color(0xFFE6E0E9),
    surfaceVariant = Color(0xFF49454F), onSurfaceVariant = Color(0xFFCAC4D0),
    surfaceTint = Color(0xFFCFBCFF),
    inverseSurface = Color(0xFFE6E0E9), inverseOnSurface = Color(0xFF322F35),
    error = Color(0xFFFFB4AB), onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
    outline = Color(0xFF948F99), outlineVariant = Color(0xFF49454F), scrim = Color.Black,
    surfaceBright = Color(0xFF3B383E), surfaceDim = Color(0xFF141218),
    surfaceContainerLowest = Color(0xFF0F0D13), surfaceContainerLow = Color(0xFF1D1B20),
    surfaceContainer = Color(0xFF211F26), surfaceContainerHigh = Color(0xFF2B2930),
    surfaceContainerHighest = Color(0xFF36343B),
)
