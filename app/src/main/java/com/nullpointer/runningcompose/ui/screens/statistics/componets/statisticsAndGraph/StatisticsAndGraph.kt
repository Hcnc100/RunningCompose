package com.nullpointer.runningcompose.ui.screens.statistics.componets.statisticsAndGraph

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun StatisticsAndGraph(
    orientation: Int,
    metricType: MetricType,
    statistics: StatisticsRun,
    listRunEntities: List<RunData>,
    modifier: Modifier = Modifier
) {

    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            StatisticsAndGraphPortrait(
                metricType = metricType,
                listRunEntities = listRunEntities,
                statistics = statistics,
                modifier = modifier
            )
        }

        else -> {
            StatisticsAndGraphLandScape(
                metricType = metricType,
                listRunEntities = listRunEntities,
                statistics = statistics,
                modifier = modifier
            )
        }
    }
}

@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true
)
@Composable
private fun StatisticsAndGraphPortraitPreview() {
    StatisticsAndGraph(
        metricType = MetricType.Meters,
        listRunEntities = RunData.listRunsExample,
        statistics = StatisticsRun(),
        orientation = Configuration.ORIENTATION_PORTRAIT
    )
}

@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
@Composable
private fun StatisticsAndGraphLandscapePreview() {
    StatisticsAndGraph(
        metricType = MetricType.Meters,
        listRunEntities = RunData.listRunsExample,
        statistics = StatisticsRun(),
        orientation = Configuration.ORIENTATION_LANDSCAPE
    )
}