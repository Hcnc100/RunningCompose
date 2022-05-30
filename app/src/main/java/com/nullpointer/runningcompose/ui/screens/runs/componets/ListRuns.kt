package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    listRuns: List<Run>?,
    listState: LazyGridState,
    actionClick: (Run) -> Unit,
    actionSelect: (Run) -> Unit,
    isSelectEnable: Boolean,
    metricType: MetricType,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    when {
        listRuns == null -> {
            LazyVerticalGrid(columns = GridCells.Adaptive(250.dp)) {
                items(10) { ItemRunFake() }
            }
        }
        listRuns.isEmpty() -> {
            EmptyScreen(animation = R.raw.empty1,
                textEmpty = stringResource(R.string.message_empty_runs))
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(250.dp),
                state = listState
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    if(!isSelectEnable)
                    SelectDropMenu(
                        sortConfig = sortConfig, changeSort = changeSort)
                }

                items(listRuns.size, key = { listRuns[it].id }) { index ->
                    ItemRun(
                        itemRun = listRuns[index],
                        actionClick = actionClick,
                        actionSelect = actionSelect,
                        isSelectEnable = isSelectEnable,
                        metricType = metricType,
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        }
    }

}

@Composable
fun SelectDropMenu(
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)) {
        MenuDropOptions(sortConfig = sortConfig, changeSort = changeSort)
        IconButton(onClick = { changeSort(null, !sortConfig.isReverse) },
            modifier = Modifier
                .size(15.dp)) {
            Icon(painter = painterResource(id = if (sortConfig.isReverse) R.drawable.ic_arrow_upward else R.drawable.ic_arrow_downward),
                contentDescription = stringResource(R.string.description_order_sort_asc_or_desc))
        }
    }

}

@Composable
fun MenuDropOptions(
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    var isDropSelect by remember {
        mutableStateOf(false)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.text_order_by), style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.width(10.dp))
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(id = sortConfig.sortType.idName),
                    modifier = Modifier.clickable {
                        isDropSelect = true
                    },
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp
                )
                Icon(painter = painterResource(id = R.drawable.ic_arrow_drop),
                    contentDescription = stringResource(R.string.description_drop_sort_menu))
            }
            DropdownMenu(
                expanded = isDropSelect,
                onDismissRequest = { isDropSelect = false },
            ) {
                SortType.values().forEach { sortType ->
                    DropdownMenuItem(onClick = {
                        isDropSelect = false
                        changeSort(sortType, null)
                    }) {
                        Text(text = stringResource(id = sortType.idName))
                    }
                }
            }
        }
    }
}