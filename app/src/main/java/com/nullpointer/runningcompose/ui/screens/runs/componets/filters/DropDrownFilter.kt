package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DropFilterAndOrder(sortConfig: SortConfig, changeSort: (SortType?, Boolean?) -> Unit) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(RunningSpacing.small),
        verticalArrangement = Arrangement.spacedBy(RunningSpacing.extraSmall),
        modifier = Modifier.fillMaxWidth().padding(horizontal = RunningSpacing.medium),
    ) {
        Text(stringResource(R.string.text_order_by), style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(vertical = RunningSpacing.medium))
        DropDownSorterOptions(sortConfig.sortType) { changeSort(it, null) }
        DropDownOrder(sortConfig.isReverse) { changeSort(null, it) }
    }
}
