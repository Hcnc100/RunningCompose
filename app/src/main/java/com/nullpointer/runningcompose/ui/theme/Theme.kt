package com.nullpointer.runningcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/** Legacy bars have fixed white/black foregrounds. Keep action tones until
 * those components migrate; neutrals, shapes and typography already share M3 tokens.
 */
internal fun ColorScheme.legacyColors(darkTheme: Boolean): androidx.compose.material.Colors {
    val colors = if (darkTheme) darkColors() else lightColors()
    return colors.copy(
        primary = Purple, primaryVariant = PurpleDark,
        secondary = Teal200, secondaryVariant = colorSelect,
        background = background, surface = surface, error = error,
        onPrimary = Color.White, onSecondary = Color.Black,
        onBackground = onBackground, onSurface = onSurface, onError = onError,
    )
}

@Composable
fun RunningComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) RunningDarkColorScheme else RunningLightColorScheme
    androidx.compose.material3.MaterialTheme(
        colorScheme = scheme,
        typography = RunningTypography,
        shapes = RunningShapes,
    ) {
        androidx.compose.material.MaterialTheme(
            colors = scheme.legacyColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}
