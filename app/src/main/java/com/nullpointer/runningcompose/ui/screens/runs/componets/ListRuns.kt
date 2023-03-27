package com.nullpointer.runningcompose.ui.screens.runs.componets

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    modifier: Modifier = Modifier,
    listRuns: LazyPagingItems<Run>,
    listState: LazyListState,
    isSelectEnable: Boolean,
    metricType: MetricType,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
    actionRun: (ActionRun, Run?) -> Unit,
    numberRuns: Int
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
                        SelectDropMenu(
                            sortConfig = sortConfig,
                            changeSort = changeSort
                        )
                }





                itemsIndexed(listRuns, key = { _, it -> it.id }) { _, run ->
                    if (run != null) {
                        ItemRun(
                            itemRun = run,
                            actionRun = actionRun,
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

fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    key: ((index: Int, item: T) -> Any)? = null,
    span: (LazyGridItemSpanScope.(item: T) -> GridItemSpan)? = null,
    itemContent: @Composable LazyGridItemScope.(item: T) -> Unit
) {
    items(
        count = items.itemCount,
        key = if (key == null) null else { index ->
            val item = items.peek(index)
            if (item == null) {
                PagingPlaceholderKey(index)
            } else {
                key(index, item)
            }
        }
    ) { index ->
        items[index]?.let {
            itemContent(it)
        }
    }
}

data class PagingPlaceholderKey(private val index: Int) : Parcelable {
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<com.nullpointer.runningcompose.ui.screens.runs.componets.PagingPlaceholderKey> =
            object :
                Parcelable.Creator<com.nullpointer.runningcompose.ui.screens.runs.componets.PagingPlaceholderKey> {
                override fun createFromParcel(parcel: Parcel) =
                    PagingPlaceholderKey(parcel.readInt())

                override fun newArray(size: Int) =
                    arrayOfNulls<com.nullpointer.runningcompose.ui.screens.runs.componets.PagingPlaceholderKey?>(
                        size
                    )
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