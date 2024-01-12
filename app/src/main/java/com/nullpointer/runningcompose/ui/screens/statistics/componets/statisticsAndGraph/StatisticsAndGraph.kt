package com.nullpointer.runningcompose.ui.screens.statistics.componets.statisticsAndGraph

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.preview.states.MetrictTypeProvider

@Composable
fun StatisticsAndGraph(
    metricType: MetricType,
    statistics: StatisticsRun,
    listRunEntities: List<RunData>,
    modifier: Modifier = Modifier,
    orientation: Int = LocalConfiguration.current.orientation,
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


@ThemePreviews
@OrientationPreviews
@Composable
private fun StatisticsAndGraphPreview(
    @PreviewParameter(MetrictTypeProvider::class) metricType: MetricType
) {
    StatisticsAndGraph(
        listRunEntities = RunData.listRunsExample,
        statistics = StatisticsRun(),
        metricType = metricType,
    )
}