package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun
import com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun.ItemRun
import com.nullpointer.runningcompose.ui.share.BlockProgress

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    numberRuns: Int,
    metricType: MetricType,
    isSelectEnable: Boolean,
    listState: LazyListState,
    modifier: Modifier = Modifier,
    listRuns: LazyPagingItems<RunData>,
    actionRun: (ActionRun, RunData?) -> Unit,
    listRunSelected:SnapshotStateMap<Long, RunData>,
    headerOrderAndFilter:@Composable () -> Unit
) {


    when (numberRuns) {
        -1 -> BlockProgress()
        0 -> EmptyRuns()
        else -> {
            LazyColumn(
                state = listState,
                modifier = modifier,
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                stickyHeader(
                    key = "header",
                    contentType = "header"
                ) {
                    if (!isSelectEnable){
                        headerOrderAndFilter()
                    }
                }

                items(
                    listRuns.itemCount,
                    key = listRuns.itemKey { it.id },
                    contentType = listRuns.itemContentType { "run"},
                ) { index ->

                    val run = listRuns[index]!!

                    ItemRun(
                        isSelect = listRunSelected.containsKey(run.id),
                        itemRunEntity = run,
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


