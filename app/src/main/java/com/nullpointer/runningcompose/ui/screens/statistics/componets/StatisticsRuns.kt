package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toAVGSpeed
import com.nullpointer.runningcompose.core.utils.toCaloriesBurned
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.core.utils.toMeters
import com.nullpointer.runningcompose.models.data.StatisticsRun
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun StatisticsRuns(
    statisticsRun: StatisticsRun,
    modifier: Modifier = Modifier,
    metricType: MetricType,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextInfo(
                    titleInfo = stringResource(R.string.title_total_time),
                    valueInfo = statisticsRun.timeRun.toFullFormatTime(true),
                    modifier = Modifier.weight(.5f)
                )
                TextInfo(
                    titleInfo = stringResource(R.string.title_total_distance),
                    valueInfo = statisticsRun.distance.toMeters(metricType),
                    modifier = Modifier.weight(.5f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TextInfo(
                    titleInfo = stringResource(R.string.title_total_calories_burned),
                    valueInfo = statisticsRun.caloriesBurned.toCaloriesBurned(metricType),
                    modifier = Modifier.weight(.5f)
                )
                TextInfo(
                    titleInfo = stringResource(R.string.title_toltal_avg_speed),
                    valueInfo = statisticsRun.AVGSpeed.toAVGSpeed(metricType),
                    modifier = Modifier.weight(.5f)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun StatisticsRunsPreview() {
    StatisticsRuns(
        metricType = MetricType.Meters,
        statisticsRun = StatisticsRun()
    )
}

@Composable
private fun TextInfo(
    titleInfo: String,
    valueInfo: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = valueInfo,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = titleInfo,
            style = MaterialTheme.typography.caption,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}