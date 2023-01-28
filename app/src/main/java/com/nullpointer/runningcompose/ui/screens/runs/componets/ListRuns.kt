package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    modifier: Modifier = Modifier,
    listRuns: LazyPagingItems<Run>,
    listState: LazyGridState,
    isSelectEnable: Boolean,
    metricType: MetricType,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
    actionRun: (ActionRun, Run) -> Unit,
) {


    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.size_item_run)),
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }, key = "header-sort") {
            if (!isSelectEnable)
                SelectDropMenu(
                    sortConfig = sortConfig,
                    changeSort = changeSort
                )
        }
        items(listRuns, key = { it.id }) { item ->
            ItemRun(
                itemRun = item,
                actionRun = actionRun,
                isSelectEnable = isSelectEnable,
                metricType = metricType,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

inline fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) {
    items(
        count = lazyPagingItems.itemCount,
        key = if (key != null) { index: Int -> key(lazyPagingItems[index]!!) } else null,
        span = if (span != null) {
            { span(lazyPagingItems[it]!!) }
        } else null,
    ) { index ->
        lazyPagingItems[index]?.let {
            itemContent(it)
        }
    }
}

@Composable
private fun SelectDropMenu(
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        MenuDropOptions(sortConfig = sortConfig, changeSort = { changeSort(it, null) })
        ReverseOrder(isReverse = sortConfig.isReverse, changeSort = { changeSort(null, it) })
    }

}

@Composable
private fun ReverseOrder(
    isReverse: Boolean,
    changeSort: (Boolean) -> Unit,
) {
    val text = remember(isReverse) {
        if (isReverse) R.string.text_asc_order else R.string.text_desc_order
    }
    val icon = remember {
        if (isReverse) R.drawable.ic_arrow_upward else R.drawable.ic_arrow_downward
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { changeSort(!isReverse) }) {
        Text(stringResource(id = text))
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(R.string.description_order_sort_asc_or_desc),
            modifier = Modifier.size(15.dp)
        )
    }
}

@Composable
private fun MenuDropOptions(
    sortConfig: SortConfig,
    changeSort: (SortType) -> Unit,
) {

    var isDropSelect by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(.7f)
    ) {
        Text(
            stringResource(R.string.text_order_by),
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = sortConfig.sortType.idName),
                    modifier = Modifier.clickable { isDropSelect = true },
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_drop),
                    contentDescription = stringResource(R.string.description_drop_sort_menu)
                )
            }
            DropdownMenu(
                expanded = isDropSelect,
                onDismissRequest = { isDropSelect = false },
            ) {
                SortType.values().forEach { sortType ->
                    DropdownMenuItem(onClick = {
                        isDropSelect = false
                        changeSort(sortType)
                    }) {
                        Text(text = stringResource(id = sortType.idName))
                    }
                }
            }
        }
    }
}