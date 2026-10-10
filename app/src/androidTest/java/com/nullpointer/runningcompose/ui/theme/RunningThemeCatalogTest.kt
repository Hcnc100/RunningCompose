package com.nullpointer.runningcompose.ui.theme

import android.graphics.Bitmap
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RunningThemeCatalogTest {
    @get:Rule val compose = createComposeRule()

    @Test fun lightThemeDisplaysActionsAndStatus() = checkCatalog(false)
    @Test fun darkThemeDisplaysActionsAndStatus() = checkCatalog(true)
    @Test fun lightThemeSupportsLargeFont() = checkCatalog(false, 2f)
    @Test fun darkThemeSupportsLargeFont() = checkCatalog(true, 2f)

    private fun checkCatalog(dark: Boolean, fontScale: Float = 1f) {
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, fontScale)) {
                RunningComposeTheme(darkTheme = dark) { RunningThemeCatalog() }
            }
        }
        compose.onNodeWithText("5.20 km").assertIsDisplayed()
        compose.onNodeWithText("Carrera seleccionada").assertIsDisplayed()
        capture("${if (dark) "dark" else "light"}-${fontScale}-top")
        compose.onNodeWithText("Iniciar carrera").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("No se pudo cargar. Inténtalo de nuevo.")
            .performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Guardando carrera").performScrollTo().assertIsDisplayed()
        compose.onNodeWithText("Acción no disponible").performScrollTo().assertIsDisplayed()
        capture("${if (dark) "dark" else "light"}-${fontScale}-bottom")
    }

    private fun capture(name: String) {
        // Advance test time; the spinner phase itself is not a snapshot contract.
        compose.mainClock.advanceTimeBy(500)
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val directory = File(instrumentation.targetContext.getExternalFilesDir(null), "theme-catalog")
        check(directory.exists() || directory.mkdirs())
        val bitmap = checkNotNull(instrumentation.uiAutomation.takeScreenshot())
        try {
            File(directory, "$name.png").outputStream().use {
                check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it))
            }
        } finally {
            bitmap.recycle()
        }
    }
}
