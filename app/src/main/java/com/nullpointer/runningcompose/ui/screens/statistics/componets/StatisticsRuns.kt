package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toAVGSpeed
import com.nullpointer.runningcompose.core.utils.toCaloriesBurned
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.core.utils.toMeters
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun StatisticsRuns(
    statisticsRun: StatisticsRun,
    modifier: Modifier = Modifier,
    metricType: MetricType,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextInfo(titleInfo = stringResource(R.string.title_total_time),
                valueInfo = statisticsRun.timeRun.toFullFormatTime(true),
                modifier = Modifier.weight(.5f))
            TextInfo(titleInfo = stringResource(R.string.title_total_distance),
                valueInfo = statisticsRun.distance.toMeters(metricType),
                modifier = Modifier.weight(.5f))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            TextInfo(titleInfo = stringResource(R.string.title_total_calories_burned),
                valueInfo = statisticsRun.caloriesBurned.toCaloriesBurned(metricType),
                modifier = Modifier.weight(.5f))
            TextInfo(titleInfo = stringResource(R.string.title_toltal_avg_speed),
                valueInfo = statisticsRun.AVGSpeed.toAVGSpeed(metricType),
                modifier = Modifier.weight(.5f))
        }
    }
}

@Composable
private fun TextInfo(titleInfo: String, valueInfo: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(text = valueInfo,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = titleInfo,
            style = MaterialTheme.typography.caption,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
    }
}