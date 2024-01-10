package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.SortType


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
            sortConfig = sortConfig,
            changeSort = { changeSort(it, null) }
        )
        DropDownOrder(
            isReverse = sortConfig.isReverse,
            changeSort = { changeSort(null, it) }
        )
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0XFFFFFF
)
@Composable
private fun DropFilterAndOrderPreview() {
    DropFilterAndOrder(
        sortConfig = SortConfig(),
        changeSort = { _, _ -> }
    )
}






