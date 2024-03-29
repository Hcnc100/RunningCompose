package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
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
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.preview.states.MetrictTypeProvider


@Composable
 fun InfoRun(
    itemRun: RunData,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val date = remember { itemRun.createAt.toDateFormat() }
    val timeDay = remember { itemRun.createAt.toDateOnlyTime(context) }
    val timeRun = remember { itemRun.timeRunInMillis.toFullFormatTime(false) }
    val distance = remember { itemRun.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRun.caloriesBurned.toCaloriesBurned(metricType) }
    val speed = remember { itemRun.avgSpeedInMeters.toAVGSpeed(metricType) }

    Row(
        modifier = modifier.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            DetailsRunText(text = stringResource(R.string.item_title_date))
            DetailsRunText(text = stringResource(R.string.item_title_hour))
            DetailsRunText(text = stringResource(R.string.item_title_duration))
            DetailsRunText(text = stringResource(R.string.item_title_distance))
            DetailsRunText(text = stringResource(R.string.item_title_speed))
            DetailsRunText(text = stringResource(R.string.item_title_calories))
        }
        Column(verticalArrangement = Arrangement.SpaceAround) {
            DetailsRunText(text = date)
            DetailsRunText(text = timeDay)
            DetailsRunText(text = timeRun)
            DetailsRunText(text = distance)
            DetailsRunText(text = speed)
            DetailsRunText(text = calories)
        }
    }
}

@Composable
private fun DetailsRunText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@SimplePreview
@Composable
private fun InfoRunPreviewMeters(
    @PreviewParameter(MetrictTypeProvider::class) metricType: MetricType
) {
    InfoRun(
        metricType = metricType,
        itemRun = RunData.runDataExample
    )
}

