package com.nullpointer.runningcompose.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RunningThemeTest {
    @Test
    fun semanticTextPairsHaveAccessibleContrastInBothModes() {
        listOf(RunningLightColorScheme, RunningDarkColorScheme).forEach { scheme ->
            val pairs = listOf(
                "primary" to (scheme.onPrimary to scheme.primary),
                "primaryContainer" to (scheme.onPrimaryContainer to scheme.primaryContainer),
                "secondary" to (scheme.onSecondary to scheme.secondary),
                "secondaryContainer" to (scheme.onSecondaryContainer to scheme.secondaryContainer),
                "tertiary" to (scheme.onTertiary to scheme.tertiary),
                "tertiaryContainer" to (scheme.onTertiaryContainer to scheme.tertiaryContainer),
                "background" to (scheme.onBackground to scheme.background),
                "surface" to (scheme.onSurface to scheme.surface),
                "surfaceContainerLowest" to (scheme.onSurface to scheme.surfaceContainerLowest),
                "surfaceContainerLow" to (scheme.onSurface to scheme.surfaceContainerLow),
                "surfaceContainer" to (scheme.onSurface to scheme.surfaceContainer),
                "surfaceContainerHigh" to (scheme.onSurface to scheme.surfaceContainerHigh),
                "surfaceContainerHighest" to (scheme.onSurface to scheme.surfaceContainerHighest),
                "surfaceVariant" to (scheme.onSurfaceVariant to scheme.surfaceVariant),
                "navigationSecondaryText" to (scheme.onSurfaceVariant to scheme.surfaceContainer),
                "error" to (scheme.onError to scheme.error),
                "errorContainer" to (scheme.onErrorContainer to scheme.errorContainer),
                "inverseSurface" to (scheme.inverseOnSurface to scheme.inverseSurface),
            )
            pairs.forEach { (role, colors) ->
                val contrast = contrast(colors.first, colors.second)
                assertTrue("$role contrast $contrast must be >= 4.5", contrast >= 4.5f)
            }
        }
    }

    @Test
    fun legacyBridgeKeepsSurfaceAndContentPairsAndMode() {
        listOf(false, true).forEach { dark ->
            val scheme = if (dark) RunningDarkColorScheme else RunningLightColorScheme
            val legacy = scheme.legacyColors(dark)
            assertEquals(!dark, legacy.isLight)
            assertEquals(scheme.background, legacy.background)
            assertEquals(scheme.onBackground, legacy.onBackground)
            assertEquals(scheme.surface, legacy.surface)
            assertEquals(scheme.onSurface, legacy.onSurface)
            assertTrue(contrast(legacy.onPrimary, legacy.primary) >= 4.5f)
            assertTrue(contrast(legacy.onSecondary, legacy.secondary) >= 4.5f)
            assertEquals(RunningTypography.bodyLarge, Typography.body1)
            assertEquals(RunningShapes.small, Shapes.small)
        }
    }

    private fun contrast(first: Color, second: Color): Float {
        val a = first.luminance()
        val b = second.luminance()
        return (maxOf(a, b) + 0.05f) / (minOf(a, b) + 0.05f)
    }
}
