package com.nullpointer.runningcompose.ui.screens.details.components

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun StatisticsRun(
    itemRunEntity: RunData,
    fontSizeBody: TextUnit,
    metricType: MetricType,
    fontSizeTitle: TextUnit,
    modifier: Modifier = Modifier,
    isStatisticsExpanded: Boolean
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .animateContentSize()
            .clickable { if (isStatisticsExpanded) isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.small
    ) {
        Column {
            TitleStatistics(
                isExpanded = isExpanded,
                fontSizeTitle = fontSizeTitle,
                isStatisticsExpanded = isStatisticsExpanded
            )
            if (isExpanded || !isStatisticsExpanded)
                InfoRun(
                    itemRunEntity = itemRunEntity,
                    modifier = Modifier.padding(10.dp),
                    metricType = metricType,
                    fontSize = fontSizeBody
                )
        }
    }
}


@Composable
fun TitleStatistics(
    isExpanded: Boolean,
    fontSizeTitle: TextUnit,
    isStatisticsExpanded: Boolean
) {
    val iconExpanded = rememberSaveable(isExpanded) {
        if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp),
            text = stringResource(id = R.string.title_screen_statistics),
            style = MaterialTheme.typography.h5.copy(fontSize = fontSizeTitle)
        )
        if (isStatisticsExpanded) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = iconExpanded),
                contentDescription = stringResource(R.string.description_arrow_statistics)
            )
        }
    }
}

@Composable
private fun InfoRun(
    itemRunEntity: RunData,
    fontSize: TextUnit,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val date = remember { itemRunEntity.timestamp.toDateFormat() }
    val timeDay = remember { itemRunEntity.timestamp.toDateOnlyTime(context) }
    val speed = remember { itemRunEntity.avgSpeedInMeters.toAVGSpeed(metricType) }
    val distance = remember { itemRunEntity.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRunEntity.caloriesBurned.toCaloriesBurned(metricType) }
    val timeRun = remember { itemRunEntity.timeRunInMillis.toFullFormatTime(true) }

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
        horizontalArrangement = Arrangement.SpaceBetween
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