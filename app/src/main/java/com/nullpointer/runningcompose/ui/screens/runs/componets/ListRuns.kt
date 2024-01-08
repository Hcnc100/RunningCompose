package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun

@Composable
fun ListRuns(
    modifier: Modifier = Modifier,
    listRuns: LazyPagingItems<RunData>,
    listState: LazyListState,
    isSelectEnable: Boolean,
    metricType: MetricType,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
    actionRun: (ActionRun, RunData?) -> Unit,
    numberRuns: Int,
    listRunSelected:SnapshotStateMap<Long, RunData>,
) {


    when (numberRuns) {
        -1 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colors.primary
                )
            }
        }
        0 -> {
            Box(Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieContainer(modifier = Modifier.size(250.dp), animation = R.raw.empty1)
                    Text(
                        text = stringResource(id = R.string.message_empty_runs),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
        else -> {
            LazyColumn(
                state = listState,
                modifier = modifier,
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                item(key = "header-sort") {
                    if (!isSelectEnable)
                        DropFilterAndOrder(
                            sortConfig = sortConfig,
                            changeSort = changeSort
                        )
                }
                items(
                    listRuns.itemCount,
                    key = listRuns.itemKey { it.id },
                    contentType = listRuns.itemContentType { "run"},
                  ) {index ->

                    val run = listRuns[index]!!

                    ItemRun(
                        isSelect = listRunSelected.containsKey(run.id),
                        itemRunEntity = run,
                        actionRun = { actionRun(it, run) },
                        isSelectEnable = isSelectEnable,
                        metricType = metricType,
//                            modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }
}


