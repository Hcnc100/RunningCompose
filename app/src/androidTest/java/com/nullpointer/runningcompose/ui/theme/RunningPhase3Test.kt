package com.nullpointer.runningcompose.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toMeters
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.actions.RunActions
import com.nullpointer.runningcompose.ui.navigation.HomeDestinations
import com.nullpointer.runningcompose.ui.screens.home.HomeNavigationBar
import com.nullpointer.runningcompose.ui.screens.home.navigationLabel
import com.nullpointer.runningcompose.ui.screens.runs.RunsScreens
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.screens.runs.componets.RunsActionBar
import com.nullpointer.runningcompose.ui.screens.runs.componets.filters.DropFilterAndOrder
import com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun.ItemRun
import com.nullpointer.runningcompose.ui.share.SelectToolbar
import java.io.File
import java.time.Instant
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RunningPhase3Test {
    @get:Rule val compose = createComposeRule()
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun text(id: Int) = context.getString(id)
    private val examples = (1..8).map { index ->
        RunData.runDataExample.copy(
            id = index.toLong(), distanceInMeters = 5100f + index * 100,
            avgSpeedInMeters = 3f, timeRunInMillis = 1_800_000L + index * 1_000,
            caloriesBurned = 350f, createAt = Instant.parse("2026-10-10T12:30:00Z").toEpochMilli() - index * 86_400_000L,
        )
    }

    @Test fun cardPreservesDetailsAndLongPressSelectionActions() {
        var selecting by mutableStateOf(false)
        var lastAction: RunActions? = null
        val run = examples.first()
        compose.setContent {
            RunningComposeTheme {
                ItemRun(selecting, run, selecting, MetricType.Kilo) { lastAction = it }
            }
        }
        val card = compose.onNodeWithText(run.distanceInMeters.toMeters(MetricType.Kilo))
        card.performClick()
        compose.runOnIdle { assertEquals(RunActions.DETAILS, lastAction) }
        card.performTouchInput { longClick() }
        compose.runOnIdle { assertEquals(RunActions.SELECT, lastAction); selecting = true }
        card.assertIsSelected().performClick()
        compose.runOnIdle { assertEquals(RunActions.SELECT, lastAction) }
    }

    @Test fun cardUpdatesUnitsWithoutRecreatingTheComposition() {
        var metric by mutableStateOf(MetricType.Meters)
        val run = examples.first()
        compose.setContent {
            RunningComposeTheme { ItemRun(false, run, false, metric, actionRun = {}) }
        }
        compose.onNodeWithText(run.distanceInMeters.toMeters(MetricType.Meters)).assertIsDisplayed()
        compose.runOnIdle { metric = MetricType.Kilo }
        compose.onNodeWithText(run.distanceInMeters.toMeters(MetricType.Kilo)).assertIsDisplayed()
    }

    @Test fun sortControlsPreserveNullableCallbackArguments() {
        var config by mutableStateOf(SortConfig())
        var lastType: SortType? = null
        var lastReverse: Boolean? = null
        compose.setContent {
            RunningComposeTheme {
                DropFilterAndOrder(config) { type, reverse ->
                    lastType = type; lastReverse = reverse
                    config = config.copy(sortType = type ?: config.sortType, isReverse = reverse ?: config.isReverse)
                }
            }
        }
        compose.onNodeWithText(text(SortType.DATE.idName)).performClick()
        compose.onNodeWithText(text(SortType.DISTANCE.idName)).performClick()
        compose.runOnIdle { assertEquals(SortType.DISTANCE, lastType); assertEquals(null, lastReverse) }
        compose.onNodeWithText(text(R.string.text_desc_order)).performClick()
        compose.runOnIdle { assertEquals(null, lastType); assertEquals(true, lastReverse) }
        compose.onNodeWithText(text(R.string.text_asc_order)).assertIsDisplayed()
    }

    @Test fun navigationLabelsRemainVisibleAndExposeSelectedDestination() {
        var selected by mutableStateOf(HomeDestinations.RunsScreen)
        compose.setContent {
            RunningComposeTheme {
                HomeNavigationBar(selected.destination.route, { selected = it })
            }
        }
        HomeDestinations.entries.forEach { destination ->
            compose.onNodeWithText(text(destination.navigationLabel)).assertIsDisplayed().performClick().assertIsSelected()
            compose.runOnIdle { assertEquals(destination, selected) }
        }
    }

    @Test fun pausedSessionUsesContinueCallbackAndSelectionUsesDelete() {
        var selecting by mutableStateOf(false)
        var starts = 0
        var deletes = 0
        compose.setContent {
            RunningComposeTheme {
                RunsActionBar(TrackingState.PAUSE, selecting, { starts++ }, { deletes++ })
            }
        }
        compose.onNodeWithText(text(R.string.m3_continue_run)).performClick()
        compose.runOnIdle { selecting = true; assertEquals(1, starts) }
        compose.onNodeWithText(text(R.string.m3_delete_runs)).performClick()
        compose.runOnIdle { assertEquals(1, deletes); assertEquals(1, starts) }
    }

    @Test fun pagingErrorCanRetryIntoLoadedCards() {
        val fail = AtomicBoolean(true)
        val flow = Pager(PagingConfig(pageSize = 10)) {
            object : PagingSource<Int, RunData>() {
                override fun getRefreshKey(state: PagingState<Int, RunData>): Int? = null
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RunData> =
                    if (fail.get()) LoadResult.Error(IllegalStateException("Fixture failure"))
                    else LoadResult.Page(examples, null, null)
            }
        }.flow
        compose.setContent {
            RunningComposeTheme {
                ListRuns(examples.size, MetricType.Kilo, false, rememberLazyGridState(),
                    listRuns = flow.collectAsLazyPagingItems(), actionRun = { _, _ -> },
                    listRunSelected = emptyMap(), headerOrderAndFilter = {})
            }
        }
        compose.waitUntil(10_000) { compose.onAllNodesWithText(text(R.string.m3_retry)).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText(text(R.string.error_load_runs)).assertIsDisplayed()
        capture("paging-error")
        fail.set(false)
        compose.onNodeWithText(text(R.string.m3_retry)).performClick()
        compose.waitUntil(10_000) {
            compose.onAllNodesWithText(examples.first().distanceInMeters.toMeters(MetricType.Kilo)).fetchSemanticsNodes().isNotEmpty()
        }
        compose.onNodeWithText(examples.first().distanceInMeters.toMeters(MetricType.Kilo)).assertIsDisplayed()
    }

    @Test fun loadedScreenLight() = checkLoadedScreen(false)
    @Test fun loadedScreenDark() = checkLoadedScreen(true)
    @Test fun loadedScreenLargeFontLight() = checkLoadedScreen(false, 2f)
    @Test fun loadedScreenLargeFontDark() = checkLoadedScreen(true, 2f)

    private fun checkLoadedScreen(dark: Boolean, scale: Float = 1f) {
        var deletes = 0
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, scale)) {
                RunningComposeTheme(dark) {
                    val selected = remember { mutableStateMapOf<Long, RunData>() }
                    val items = remember { MutableStateFlow(PagingData.from(examples)) }.collectAsLazyPagingItems()
                    Scaffold(
                        topBar = { SelectToolbar(R.string.app_name, R.plurals.selected_items, selected.size, { selected.clear() }) },
                        bottomBar = { HomeNavigationBar(HomeDestinations.RunsScreen.destination.route, {}) },
                    ) { padding ->
                        Box(Modifier.padding(padding).consumeWindowInsets(padding)) {
                            RunsScreens(
                                numberRuns = examples.size, metricType = MetricType.Kilo,
                                isSelectEnable = selected.isNotEmpty(), scaffoldState = rememberScaffoldState(),
                                lazyGridState = rememberLazyGridState(), listRuns = items,
                                actionRunScreen = { action, run ->
                                    if (action == RunActions.SELECT) {
                                        if (selected.containsKey(run.id)) selected.remove(run.id) else selected[run.id] = run
                                    }
                                },
                                listRunsSelected = selected, stateTracking = TrackingState.WAITING,
                                actionAddRun = {}, actionRemoveRun = { deletes++ },
                                sortConfig = SortConfig(), changeSort = { _, _ -> },
                            )
                        }
                    }
                }
            }
        }
        val first = examples.first().distanceInMeters.toMeters(MetricType.Kilo)
        compose.waitUntil(10_000) { compose.onAllNodesWithText(first).fetchSemanticsNodes().isNotEmpty() }
        compose.onNodeWithText(first).assertIsDisplayed()
        val prefix = "${if (dark) "dark" else "light"}-$scale"
        HomeDestinations.entries.forEach { destination ->
            val layouts = mutableListOf<TextLayoutResult>()
            compose.onNodeWithText(text(destination.navigationLabel), useUnmergedTree = true)
                .performSemanticsAction(SemanticsActions.GetTextLayoutResult) { it(layouts) }
            assertTrue("Navigation label ${text(destination.navigationLabel)} must not truncate: " +
                layouts.map { "${it.size}, paragraph=${it.multiParagraph.width}x${it.multiParagraph.height}, widthOverflow=${it.didOverflowWidth}, heightOverflow=${it.didOverflowHeight}, lines=${it.lineCount}, ellipsis=${it.isLineEllipsized(0)}" },
                // Paragraph width retains the parent's maxWidth even for short labels;
                // didOverflowWidth therefore flags "Runs" despite no clipped glyphs.
                layouts.isNotEmpty() && layouts.none { layout ->
                    layout.didOverflowHeight || (0 until layout.lineCount).any { layout.isLineEllipsized(it) }
                })
        }
        capture("$prefix-list")
        compose.onNodeWithText(first).performTouchInput { longClick() }
        compose.onNodeWithText(first).assertIsSelected()
        compose.onNodeWithText(text(R.string.m3_delete_runs)).assertIsDisplayed()
        capture("$prefix-selected")
        compose.onNodeWithText(text(R.string.m3_delete_runs)).performClick()
        compose.runOnIdle { assertEquals(1, deletes) }
        compose.onNodeWithContentDescription(text(R.string.description_clear_selection)).performClick()
        compose.onNodeWithText(first).assertIsNotSelected()
        compose.onNode(hasScrollToIndexAction()).performScrollToIndex(examples.lastIndex)
        compose.onNodeWithText(examples.last().distanceInMeters.toMeters(MetricType.Kilo)).assertIsDisplayed()
        val action = compose.onNodeWithText(text(R.string.m3_start_run)).assertIsDisplayed().fetchSemanticsNode().boundsInRoot
        val last = compose.onNodeWithText(examples.last().distanceInMeters.toMeters(MetricType.Kilo)).fetchSemanticsNode().boundsInRoot
        assertTrue("Last card must not overlap the reserved action", last.bottom <= action.top)
        capture("$prefix-bottom")
    }

    private fun capture(name: String) {
        compose.waitForIdle()
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val directory = File(instrumentation.targetContext.getExternalFilesDir(null), "phase3")
        check(directory.exists() || directory.mkdirs())
        val bitmap = checkNotNull(instrumentation.uiAutomation.takeScreenshot())
        try {
            File(directory, "$name.png").outputStream().use { check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)) }
        } finally { bitmap.recycle() }
    }
}
