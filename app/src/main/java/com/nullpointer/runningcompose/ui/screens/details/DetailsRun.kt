package com.nullpointer.runningcompose.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.navigation.RootNavGraph
import com.nullpointer.runningcompose.ui.screens.runs.componets.MapRunItem
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun DetailsRun(
    navigator: DestinationsNavigator,
    itemsRun: Run,
    metricType: MetricType,
) {
    Scaffold(
        topBar = {
            ToolbarBack(title = stringResource(R.string.title_details),
                actionBack = navigator::popBackStack)
        }
    ) {
        Column {
            MapRunItem(
                itemsRun,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(7f)
            )
            Card(modifier = Modifier
                .padding(10.dp)
                .weight(3f), shape = RoundedCornerShape(10.dp)) {
                InfoRun(
                    itemRun = itemsRun,
                    modifier = Modifier.padding(10.dp),
                    metricType = metricType
                )
            }
        }
    }
}

@Composable
private fun InfoRun(
    itemRun: Run,
    modifier: Modifier = Modifier,
    metricType: MetricType,
) {
    val context = LocalContext.current
    val date = remember { itemRun.timestamp.toDateFormat() }
    val timeDay = remember { itemRun.timestamp.toDateOnlyTime(context) }
    val timeRun = remember { itemRun.timeRunInMillis.toFullFormatTime(true) }
    val distance = remember { itemRun.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRun.caloriesBurned.toCaloriesBurned(metricType) }
    val speed = remember { itemRun.avgSpeedInMeters.toAVGSpeed(metricType) }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        TitleAndInfo(stringResource(R.string.item_title_date), date)
        TitleAndInfo(stringResource(R.string.item_title_hour), timeDay)
        TitleAndInfo(stringResource(R.string.item_title_duration), timeRun)
        TitleAndInfo(stringResource(R.string.item_title_distance), distance)
        TitleAndInfo(stringResource(R.string.item_title_speed), speed)
        TitleAndInfo(stringResource(R.string.item_title_calories), calories)
    }

}

@Composable
fun TitleAndInfo(
    title: String,
    info: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        DetailsRunText(text = title, modifier = Modifier.weight(0.4f))
        Spacer(modifier = Modifier.width(10.dp))
        DetailsRunText(text = info, modifier = Modifier.weight(0.6f))
    }
}

@Composable
private fun DetailsRunText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.body1,
    )
}