package com.nullpointer.runningcompose.ui.screens.statistics.componets.statisticsAndGraph

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.preview.config.PortraitPreview
import com.nullpointer.runningcompose.ui.screens.statistics.componets.GraphRuns
import com.nullpointer.runningcompose.ui.screens.statistics.componets.StatisticsRuns

@Composable
fun StatisticsAndGraphPortrait(
    metricType: MetricType,
    statistics: StatisticsRun,
    modifier: Modifier = Modifier,
    listRunEntities: List<RunData>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatisticsRuns(
            statisticsRun = statistics,
            metricType = metricType,
        )
        GraphRuns(
            list = listRunEntities,
            metricType = metricType,
            modifier = Modifier.weight(1F)
        )
    }
}

@PortraitPreview
@Composable
private fun StatisticsAndGraphPortraitPreview() {
    StatisticsAndGraphPortrait(
        metricType = MetricType.Kilo,
        listRunEntities = RunData.listRunsExample,
        statistics = StatisticsRun(
            timeRun = 1000L,
            distance = 1000F,
            caloriesBurned = 1000F,
            AVGSpeed = 1000F
        )
    )
}