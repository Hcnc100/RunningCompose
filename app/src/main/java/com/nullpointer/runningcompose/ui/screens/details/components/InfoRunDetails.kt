package com.nullpointer.runningcompose.ui.screens.details.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toAVGSpeed
import com.nullpointer.runningcompose.core.utils.toCaloriesBurned
import com.nullpointer.runningcompose.core.utils.toDateFormat
import com.nullpointer.runningcompose.core.utils.toDateOnlyTime
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.core.utils.toMeters
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun InfoRunDetails(
    runData: RunData,
    fontSize: TextUnit,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val date = remember { runData.timestamp.toDateFormat() }
    val timeDay = remember { runData.timestamp.toDateOnlyTime(context) }
    val speed = remember { runData.avgSpeedInMeters.toAVGSpeed(metricType) }
    val distance = remember { runData.distanceInMeters.toMeters(metricType) }
    val calories = remember { runData.caloriesBurned.toCaloriesBurned(metricType) }
    val timeRun = remember { runData.timeRunInMillis.toFullFormatTime(true) }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TitleAndInfo(stringResource(R.string.item_title_date), date, fontSize)
        TitleAndInfo(stringResource(R.string.item_title_hour), timeDay, fontSize)
        TitleAndInfo(stringResource(R.string.item_title_duration), timeRun, fontSize)
        TitleAndInfo(stringResource(R.string.item_title_distance), distance, fontSize)
        TitleAndInfo(stringResource(R.string.item_title_speed), speed, fontSize)
        TitleAndInfo(stringResource(R.string.item_title_calories), calories, fontSize)
    }

}


@Composable
private fun TitleAndInfo(
    info: String,
    title: String,
    fontSize: TextUnit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = info,
            style = MaterialTheme.typography.body1.copy(fontSize = fontSize),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.body1.copy(fontSize = fontSize),
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
fun InfoRunDetailsPreview() {
    InfoRunDetails(
        runData = RunData(
            mapConfig = MapConfig(),
            caloriesBurned = 0F,
            timestamp = 0,
            listPolyLineEncode = emptyList(),
            distanceInMeters = 0F,
            pathImgRun = null,
            timeRunInMillis = 0,
            avgSpeedInMeters = 0F,
            id = 0
        ),
        fontSize = 12.sp,
        metricType = MetricType.Meters,
    )
}