package com.nullpointer.runningcompose.ui.screens.details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun StatisticsRun(
    itemRunEntity: RunData,
    fontSizeBody: TextUnit,
    metricType: MetricType,
    fontSizeTitle: TextUnit,
    modifier: Modifier = Modifier,
    canExpanded: Boolean
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { if (canExpanded) isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.small
    ) {
        Column {
            TitleStatistics(
                isExpanded = isExpanded,
                fontSizeTitle = fontSizeTitle,
                canExpanded = canExpanded
            )
            if (isExpanded || !canExpanded)
                InfoRunDetails(
                    runData = itemRunEntity,
                    modifier = Modifier.padding(10.dp),
                    metricType = metricType,
                    fontSize = fontSizeBody
                )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun StatisticsRunCanExpandedPreview() {
    StatisticsRun(
        canExpanded = true,
        fontSizeBody = 12.sp,
        fontSizeTitle = 12.sp,
        metricType = MetricType.Meters,
        itemRunEntity = RunData.runDataExample
    )
}


@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun StatisticsRunCannotExpandedPreview() {
    StatisticsRun(
        canExpanded = false,
        fontSizeBody = 12.sp,
        fontSizeTitle = 12.sp,
        metricType = MetricType.Meters,
        itemRunEntity = RunData.runDataExample
    )
}

