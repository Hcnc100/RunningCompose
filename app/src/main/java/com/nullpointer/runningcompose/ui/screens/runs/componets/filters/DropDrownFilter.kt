package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.preview.states.SortTypeProvider


@Composable
fun DropFilterAndOrder(
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
        DropDownSorterOptions(
            sortType = sortConfig.sortType,
            changeSort = { changeSort(it, null) }
        )
        DropDownOrder(
            isReverse = sortConfig.isReverse,
            changeSort = { changeSort(null, it) }
        )
    }
}


@SimplePreview
@Composable
private fun DropFilterAndOrderReversePreview(
    @PreviewParameter(SortTypeProvider::class) sortType: SortType,
) {
    DropFilterAndOrder(
        sortConfig = SortConfig(
            sortType = sortType,
            isReverse = true,
        ),
        changeSort = { _, _ -> }
    )
}


@SimplePreview
@Composable
private fun DropFilterAndOrderPreview(
    @PreviewParameter(SortTypeProvider::class) sortType: SortType,
) {
    DropFilterAndOrder(
        sortConfig = SortConfig(
            sortType = sortType,
            isReverse = false,
        ),
        changeSort = { _, _ -> }
    )
}







