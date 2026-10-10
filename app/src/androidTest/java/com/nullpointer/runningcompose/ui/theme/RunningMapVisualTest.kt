package com.nullpointer.runningcompose.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle
import com.nullpointer.runningcompose.ui.screens.tracking.componets.map.MapTracking
import com.nullpointer.runningcompose.ui.screens.tracking.componets.map.TrackingPolylineRenderer
import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RunningMapVisualTest {
    @get:Rule val compose = createComposeRule()

    @Test fun emptyRoutesStyleChangesAndPausedSegmentsRenderWithoutDuplicates() {
        lateinit var view: MapView
        var map: GoogleMap? = null
        compose.setContent {
            RunningComposeTheme {
                val state = rememberMapWithLifecycle()
                SideEffect { view = state }
                MapTracking(null, state, DrawPolyData(emptyList()), Modifier.fillMaxSize())
            }
        }
        compose.runOnIdle { view.getMapAsync { map = it } }
        compose.waitUntil(15_000) { map != null }
        val renderer = TrackingPolylineRenderer()
        val first = listOf(LatLng(37.4220, -122.0840), LatLng(37.4220, -122.0830), LatLng(37.4226, -122.0830))
        val second = listOf(LatLng(37.4226, -122.0826), LatLng(37.4230, -122.0826), LatLng(37.4230, -122.0816))
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        compose.runOnIdle {
            renderer.update(map!!, DrawPolyData(emptyList()))
            renderer.update(map!!, DrawPolyData(emptyList()))
            assertEquals(0, renderer.lines.size)
            renderer.update(map!!, DrawPolyData(listOf(first)))
            val originalId = renderer.lines.single().id
            renderer.update(map!!, DrawPolyData(listOf(first + first.last())))
            assertEquals(originalId, renderer.lines.single().id)
            assertEquals(4, renderer.lines.single().points.size)
        }
        val samples = listOf(
            Triple("lite-purple", MapStyle.LITE, 0xFF512DA8.toInt()),
            Triple("lite-teal", MapStyle.LITE, 0xFF006A60.toInt()),
            Triple("midnight-lavender", MapStyle.MIDNIGHT, 0xFFCFBCFF.toInt()),
            Triple("midnight-amber", MapStyle.MIDNIGHT, 0xFFFFC857.toInt()),
        )
        samples.forEach { (name, style, color) ->
            compose.runOnIdle {
                val previousId = renderer.lines.first().id
                map!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, style.styleRawRes))
                renderer.update(map!!, DrawPolyData(listOf(first, second), MapConfig(weight = 10, style = style, colorValue = color)))
                assertEquals(2, renderer.lines.size)
                assertNotEquals(previousId, renderer.lines.first().id)
                assertEquals(first, renderer.lines.first().points)
                assertEquals(second, renderer.lines.last().points)
                assertEquals(color, renderer.lines.first().color)
                assertEquals(10f, renderer.lines.first().width)
                map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.4225, -122.0828), 16f))
            }
            // Map tiles render outside Compose's idling clock.
            Thread.sleep(2500)
            val directory = File(context.getExternalFilesDir(null), "map-visual").apply { mkdirs() }
            val bitmap = checkNotNull(InstrumentationRegistry.getInstrumentation().uiAutomation.takeScreenshot())
            try { File(directory, "$name.png").outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) } }
            finally { bitmap.recycle() }
        }
        compose.runOnIdle {
            renderer.update(map!!, DrawPolyData(emptyList()))
            assertEquals(0, renderer.lines.size)
        }
    }
}
