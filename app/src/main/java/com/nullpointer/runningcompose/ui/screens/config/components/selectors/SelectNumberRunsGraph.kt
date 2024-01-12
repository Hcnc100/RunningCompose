package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig


private val mapsNumberRunsGraph =
    listOf(5, 10, 15, 20, 25, 50, 100).associateBy({ it }, { null })

@Composable
fun SelectNumberRunsGraph(
    numberRunsGraph: Int,
    changeNumberRunsGraph: (Int) -> Unit,
) {
    SelectOptionConfig(
        titleField = stringResource(R.string.number_runs_in_the_graph),
        onChange = changeNumberRunsGraph,
        textCurrentSelected =numberRunsGraph.toString(),
        listItemsAndNames = mapsNumberRunsGraph
    )
}

@SimplePreview
@Composable
fun SelectNumberRunsGraphPreview() {
    SelectNumberRunsGraph(
        numberRunsGraph = 5,
        changeNumberRunsGraph = {}
    )
}