package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.actions.RunActions
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun.ItemRun
import com.nullpointer.runningcompose.ui.share.BlockProgress
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    numberRuns: Int,
    metricType: MetricType,
    isSelectEnable: Boolean,
    listState: LazyGridState,
    modifier: Modifier = Modifier,
    listRuns: LazyPagingItems<RunData>,
    actionRun: (RunActions, RunData) -> Unit,
    listRunSelected: Map<Long, RunData>,
    headerOrderAndFilter: @Composable () -> Unit
) {
    when (numberRuns) {
        -1 -> BlockProgress()
        0 -> EmptyRuns()
        else -> {
            Column(modifier = modifier) {
                if (!isSelectEnable) {
                    headerOrderAndFilter()
                }
                LazyVerticalGrid(
                    state = listState,
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    columns = GridCells.Adaptive(minSize = 350.dp)
                ) {

                    items(
                        listRuns.itemCount,
                        key = listRuns.itemKey { it.id },
                        contentType = listRuns.itemContentType { "run" },
                    ) { index ->
                        val run = listRuns[index]!!
                        ItemRun(
                            isSelect = listRunSelected.containsKey(run.id),
                            runData = run,
                            actionRun = { actionRun(it, run) },
                            isSelectEnable = isSelectEnable,
                            metricType = metricType,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                }
            }
        }
    }
}

@ThemePreviews
@OrientationPreviews
@Composable
private fun ListRunsLandscapeOrientation() {
    val pagingData = PagingData.from(RunData.listRunsExample)
    val fakeDataFlow = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    ListRuns(
        listRuns = fakeDataFlow,
        listRunSelected = SnapshotStateMap(),
        listState = rememberLazyGridState(),
        actionRun = { _, _ -> },
        isSelectEnable = false,
        numberRuns = RunData.listRunsExample.size,
        headerOrderAndFilter = {},
        metricType = MetricType.Meters

    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
)
@Composable
private fun ListRunsPortraitLoadPreview() {
    val pagingData = PagingData.from(emptyList<RunData>())
    val fakeDataFlow = MutableStateFlow(pagingData).collectAsLazyPagingItems()

    ListRuns(
        listRuns = fakeDataFlow,
        listRunSelected = SnapshotStateMap(),
        listState = rememberLazyGridState(),
        actionRun = { _, _ -> },
        isSelectEnable = false,
        numberRuns = -1,
        headerOrderAndFilter = {},
        metricType = MetricType.Meters

    )
}

