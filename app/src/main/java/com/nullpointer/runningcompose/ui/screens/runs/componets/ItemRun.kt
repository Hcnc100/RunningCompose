package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRun(
    itemRun: Run,
    isSelectEnable: Boolean,
    actionRun: (ActionRun, Run) -> Unit,
    metricType: MetricType,
    modifier: Modifier = Modifier
) {

    val colorSelect by derivedStateOf { if (itemRun.isSelected) Color.Cyan.copy(alpha = 0.5f) else Color.Transparent }

    Card(
        modifier = modifier
            .padding(3.dp)
            .combinedClickable(
                onClick = {
                    if (isSelectEnable) {
                        actionRun(ActionRun.SELECT, itemRun)
                    } else {
                        actionRun(ActionRun.DETAILS, itemRun)
                    }
                },
                onLongClick = {
                    if (!isSelectEnable) {
                        actionRun(ActionRun.SELECT, itemRun)
                    }
                }
            ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(modifier = Modifier
            .drawBehind { drawRect(colorSelect) }
            .padding(10.dp)
            .height(150.dp)) {
            // * waiting to take snapshot for maps compose
            Box(modifier = Modifier
                .weight(.5f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.primary)) {

                AsyncImage(
                    model = R.drawable.ic_run,
                    placeholder = painterResource(id = R.drawable.ic_run),
                    error = painterResource(id = R.drawable.ic_error_img),
                    contentDescription = stringResource(R.string.description_current_run_img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            InfoRun(itemRun = itemRun,
                modifier = Modifier.weight(.5f),
                metricType = metricType
            )
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
    val timeRun = remember { itemRun.timeRunInMillis.toFullFormatTime(false) }
    val distance = remember { itemRun.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRun.caloriesBurned.toCaloriesBurned(metricType) }
    val speed = remember { itemRun.avgSpeedInMeters.toMeters(metricType) }

    Row(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = stringResource(R.string.item_title_date))
            TextMiniTitle(text = stringResource(R.string.item_title_hour))
            TextMiniTitle(text = stringResource(R.string.item_title_duration))
            TextMiniTitle(text = stringResource(R.string.item_title_distance))
            TextMiniTitle(text = stringResource(R.string.item_title_speed))
            TextMiniTitle(text = stringResource(R.string.item_title_calories))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = date)
            TextMiniTitle(text = timeDay)
            TextMiniTitle(text = timeRun)
            TextMiniTitle(text = distance)
            TextMiniTitle(text = calories)
            TextMiniTitle(text = speed)
        }
    }
}

@Composable
private fun TextMiniTitle(
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