package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.actions.RunActions


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRun(
    isSelect: Boolean,
    runData: RunData,
    isSelectEnable: Boolean,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    actionRun: (RunActions) -> Unit,
) {

    val colorSelect by animateColorAsState(
        if (isSelect) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.surface,
        label = "ANIMATE_SELECT_COLOR"
    )

    Surface(
        color = colorSelect,
        shape = MaterialTheme.shapes.small,
        elevation = 3.dp,
        modifier = modifier
            .combinedClickable(
                onClick = {
                    val action = if (isSelectEnable) RunActions.SELECT else RunActions.DETAILS
                    actionRun(action)
                },
                onLongClick = { if (!isSelectEnable) actionRun(RunActions.SELECT) }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment =  Alignment.CenterVertically
        ) {
            ImageRun(
                pathImage = runData.pathImgRun,
                modifier = Modifier.weight(.5f)
            )
            InfoRun(
                itemRun = runData,
                modifier = Modifier.weight(.5f),
                metricType = metricType
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ItemRunUnselectPreview() {
    ItemRun(
        isSelect = false,
        runData = RunData.runDataExample,
        isSelectEnable = false,
        metricType = MetricType.Meters,
        actionRun = {}
    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ItemRunSelectPreview() {
    ItemRun(
        isSelect = true,
        runData = RunData.runDataExample,
        isSelectEnable = false,
        metricType = MetricType.Meters,
        actionRun = {}
    )
}

