package com.nullpointer.runningcompose.ui.screens.details

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nullpointer.runningcompose.ui.navigation.RootNavGraph
import com.nullpointer.runningcompose.ui.screens.runs.componets.MapRunItem
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun DetailsRun(
    navigator: DestinationsNavigator,
    itemsRun: Run,
    metricType: MetricType,
    detailsState: OrientationScreenState = rememberOrientationScreenState()
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val iconExpanded by remember {
        derivedStateOf {
            if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        }

    }
    Scaffold(
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_details),
                actionBack = navigator::popBackStack
            )
        }
    ) {
        when (detailsState.orientation) {
            ORIENTATION_LANDSCAPE -> {
                Box {
                    MapRunItem(
                        itemsRun,
                        alignmentButton = Alignment.BottomEnd,
                        modifier = Modifier.fillMaxSize()
                    )
                    Card(
                        modifier = Modifier
                            .padding(5.dp)
                            .align(Alignment.TopStart)
                            .width(250.dp)
                            .animateContentSize()
                            .clickable { isExpanded = !isExpanded },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.title_screen_statistics),
                                    modifier = Modifier
                                        .padding(5.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.h6,
                                    fontSize = 16.sp
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
                Column {
                    MapRunItem(
                        itemsRun,
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
                        shape = RoundedCornerShape(10.dp)
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