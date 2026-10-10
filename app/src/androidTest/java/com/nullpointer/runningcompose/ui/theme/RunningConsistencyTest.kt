package com.nullpointer.runningcompose.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.unit.Density
import androidx.test.platform.app.InstrumentationRegistry
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.actions.IntroActions
import com.nullpointer.runningcompose.ui.screens.intro.componets.*
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions
import com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices.TrackingControls
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class RunningConsistencyTest {
    @get:Rule val compose = createComposeRule()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun text(id: Int) = context.getString(id)

    @Test fun trackingControlsPreserveAllCallbacks() {
        var state by mutableStateOf(TrackingState.WAITING)
        val actions = mutableListOf<TrackingActions>()
        compose.setContent { RunningComposeTheme { TrackingControls(state, { actions += it }) } }
        compose.onNodeWithText(text(R.string.m3_start_run)).performClick()
        compose.runOnIdle { state = TrackingState.TRACKING }
        compose.onNodeWithText(text(R.string.m3_pause_run)).performClick()
        compose.onNodeWithText(text(R.string.m3_save_run)).performClick()
        compose.runOnIdle { state = TrackingState.PAUSE }
        compose.onNodeWithText(text(R.string.m3_continue_run)).performClick()
        compose.runOnIdle { assertEquals(listOf(TrackingActions.START, TrackingActions.RESUME, TrackingActions.SAVED, TrackingActions.START), actions) }
    }

    @Test fun introductionLight() = intro(false, 1f)
    @Test fun introductionDarkLargeFont() = intro(true, 2f)

    private fun intro(dark: Boolean, scale: Float) {
        // Lottie uses a frame loop; freeze the test clock for deterministic captures.
        compose.mainClock.autoAdvance = false
        var page by mutableStateOf(0)
        var started = false
        var permissionRequests = 0
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, scale)) {
                RunningComposeTheme(dark) {
                    Scaffold { padding ->
                        Column(Modifier.fillMaxSize().padding(padding)) {
                            when (page) {
                                0 -> DescriptionScreen(Modifier.weight(1f))
                                1 -> LocationScreen(Modifier.weight(1f), { permissionRequests++ }, true)
                                2 -> NotifyScreen(Modifier.weight(1f), { permissionRequests++ }, true)
                            }
                            PageIndicator(page, 3, MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.outlineVariant, Modifier.align(Alignment.CenterHorizontally))
                            ButtonsPrevAndNext(page == 0, page == 2, Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
                                when (it) {
                                    IntroActions.PREV -> page--
                                    IntroActions.NEXT -> page++
                                    IntroActions.START -> started = true
                                }
                            }
                        }
                    }
                }
            }
        }
        compose.mainClock.advanceTimeBy(1000)
        compose.onNodeWithText(text(R.string.title_prev_button)).assertIsNotEnabled()
        repeat(3) { index ->
            val label = if (index == 2) R.string.title_start_button else R.string.title_next_button
            compose.onNodeWithText(text(label)).assertIsDisplayed()
            capture("intro-${if (dark) "dark" else "light"}-$scale-$index")
            if (index > 0) {
                compose.onNodeWithText(text(R.string.title_grant_permission)).performScrollTo().performClick()
                compose.mainClock.advanceTimeByFrame()
                val description = if (index == 1) R.string.title_description_permission else R.string.title_notify_description
                compose.onNodeWithText(text(description)).performScrollTo().assertIsDisplayed()
            }
            compose.onNodeWithText(text(label)).performClick()
            compose.mainClock.advanceTimeBy(1000)
        }
        compose.runOnIdle { assertEquals(true, started); assertEquals(2, permissionRequests) }
        compose.onNodeWithText(text(R.string.title_prev_button)).performClick()
        compose.mainClock.advanceTimeBy(1000)
        compose.runOnIdle { assertEquals(1, page) }
    }

    private fun capture(name: String) {
        compose.waitForIdle()
        // UiAutomation captures SurfaceFlinger, which may trail Compose's clock.
        Thread.sleep(300)
        val directory = File(context.getExternalFilesDir(null), "consistent-shell").apply { mkdirs() }
        val bitmap = checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot())
        try { File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
        finally { bitmap.recycle() }
    }
}
