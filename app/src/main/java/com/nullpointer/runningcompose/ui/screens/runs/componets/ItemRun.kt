package com.nullpointer.runningcompose.ui.screens.runs.componets

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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
    metricType: MetricType,
    modifier: Modifier = Modifier,
    actionRun: (ActionRun, Run) -> Unit,
) {

    val colorSelect by animateColorAsState(
        if (itemRun.isSelected) MaterialTheme.colors.secondaryVariant else Color.Transparent
    )

    Surface(
        color = colorSelect,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .padding(3.dp)
            .combinedClickable(
                onClick = {
                    val action = if (isSelectEnable) ActionRun.SELECT else ActionRun.DETAILS
                    actionRun(action, itemRun)
                },
                onLongClick = { if (!isSelectEnable) actionRun(ActionRun.SELECT, itemRun) }
            )
    ) {
        Row(modifier = Modifier
            .padding(10.dp)
            .height(150.dp)) {
            // * waiting to take snapshot for maps compose
            ImageRun(
                data = itemRun.pathImgRun,
                modifier = Modifier.weight(.5f)
            )

            Spacer(modifier = Modifier.width(20.dp))
            InfoRun(
                itemRun = itemRun,
                modifier = Modifier.weight(.5f),
                metricType = metricType
            )
        }
    }
}

@Composable
private fun ImageRun(
    data: Any?,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(data)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_map),
        error = painterResource(id = R.drawable.ic_broken_image),
    )

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.description_current_run_img),
        contentScale = if (painter.isSuccess) ContentScale.Crop else ContentScale.Fit,
        colorFilter = if(painter.isSuccess) null else ColorFilter.tint(getGrayColor()),
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
    )
}


@Composable
private fun InfoRun(
    itemRun: Run,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context:Context=LocalContext.current
) {
    val date = remember { itemRun.timestamp.toDateFormat() }
    val timeDay = remember { itemRun.timestamp.toDateOnlyTime(context) }
    val timeRun = remember { itemRun.timeRunInMillis.toFullFormatTime(false) }
    val distance = remember { itemRun.distanceInMeters.toMeters(metricType) }
    val calories = remember { itemRun.caloriesBurned.toCaloriesBurned(metricType) }
    val speed = remember { itemRun.avgSpeedInMeters.toAVGSpeed(metricType) }

    Row(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            DetailsRunText(text = stringResource(R.string.item_title_date))
            DetailsRunText(text = stringResource(R.string.item_title_hour))
            DetailsRunText(text = stringResource(R.string.item_title_duration))
            DetailsRunText(text = stringResource(R.string.item_title_distance))
            DetailsRunText(text = stringResource(R.string.item_title_speed))
            DetailsRunText(text = stringResource(R.string.item_title_calories))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
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