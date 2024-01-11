package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig


private val listWeight = (3..10).associateBy({it}, {null} )
@Composable
 fun SelectMapWeight(
    currentWeightMap: Int,
    changeWeight: (Int) -> Unit,
) {

    SelectOptionConfig(
        titleField = stringResource(R.string.title_weight_map_line),
        textCurrentSelected = currentWeightMap.toString(),
        listItemsAndNames = listWeight,
        onChange = changeWeight
    )
}

@SimplePreview
@Composable
fun SelectMapWeightPreview() {
    SelectMapWeight(
        currentWeightMap = 3,
        changeWeight = {}
    )
}