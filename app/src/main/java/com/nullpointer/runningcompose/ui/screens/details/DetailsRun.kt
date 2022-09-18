package com.nullpointer.runningcompose.ui.screens.details

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.runs.componets.MapRunItem
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph
@Destination
@Composable
fun DetailsRun(
    itemsRun: Run,
    metricType: MetricType,
    navigator: DestinationsNavigator,
    detailsState: OrientationScreenState = rememberOrientationScreenState()
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val iconExpanded = remember(isExpanded) {
        if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
    }
    Scaffold(
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_details),
                actionBack = navigator::popBackStack
            )
        }
    ) { padding ->
        when (detailsState.orientation) {
            ORIENTATION_LANDSCAPE -> {
                Box {
                    MapRunItem(
                        itemRun = itemsRun,
                        alignmentButton = Alignment.BottomEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.TopStart)
                            .width(250.dp)
                            .animateContentSize()
                            .clickable { isExpanded = !isExpanded },
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(5.dp),
                                    style = MaterialTheme.typography.h6,
                                    text = stringResource(id = R.string.title_screen_statistics)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Icon(
                                    painter = painterResource(id = iconExpanded),
                                    contentDescription = stringResource(R.string.description_arrow_statistics)
                                )
                            }
                            if (isExpanded)
                                InfoRun(
                                    itemRun = itemsRun,
                                    modifier = Modifier.padding(10.dp),
                                    metricType = metricType
                                )
                        }
                    }

                }
            }
            ORIENTATION_PORTRAIT -> {
                Column(modifier = Modifier.padding(padding)) {
                    MapRunItem(
                        itemRun = itemsRun,
                        alignmentButton = Alignment.BottomEnd,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .weight(6.5f)
                    )
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(3.5f)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Column {
                            Text(
                                text = stringResource(id = R.string.title_screen_statistics),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h5
                            )
                            InfoRun(
                                itemRun = itemsRun,
                                modifier = Modifier.padding(10.dp),
                                metricType = metricType
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRun(
    itemRun: Run,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val date = remember { itemRun.timestamp.toDateFormat() }
    val timeDay = remember { itemRun.timestamp.toDateOnlyTime(context) }
    val speed = remember { itemRun.avgSpeedInMeters.toAVGSpeed(metricType) }
    val distance = remember { itemRun.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRun.caloriesBurned.toCaloriesBurned(metricType) }
    val timeRun = remember { itemRun.timeRunInMillis.toFullFormatTime(true) }

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
private fun TitleAndInfo(
    title: String,
    info: String
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        DetailsRunText(text = title)
        Spacer(modifier = Modifier.width(10.dp))
        DetailsRunText(text = info)
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