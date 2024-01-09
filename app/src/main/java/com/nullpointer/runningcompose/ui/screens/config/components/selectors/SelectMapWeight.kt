package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
fun SelectMapWeightPreview() {
    SelectMapWeight(
        currentWeightMap = 3,
        changeWeight = {}
    )
}