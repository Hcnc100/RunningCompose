package com.nullpointer.runningcompose.ui.screens.runs.componets

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRun(
    isSelect: Boolean,
    itemRunEntity: RunData,
    isSelectEnable: Boolean,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    actionRun: (ActionRun) -> Unit,
) {

    val colorSelect by animateColorAsState(
        if (isSelect) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.surface,
        label = "ANIMATE_SELECT_COLOR"
    )

    Surface(
        color = colorSelect,
        shape = MaterialTheme.shapes.small,
        elevation = 3.dp,
        modifier = modifier
            .combinedClickable(
                onClick = {
                    val action = if (isSelectEnable) ActionRun.SELECT else ActionRun.DETAILS
                    actionRun(action)
                },
                onLongClick = { if (!isSelectEnable) actionRun(ActionRun.SELECT) }
            )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .height(150.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // * waiting to take snapshot for maps compose
            ImageRun(
                data = itemRunEntity.pathImgRun,
                modifier = Modifier.weight(.5f)
            )
            InfoRun(
                itemRun = itemRunEntity,
                modifier = Modifier.weight(.5f),
                metricType = metricType
            )
        }
    }
}

@Composable
private fun ImageRun(
    data: String?,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(data)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_map),
        error = painterResource(id = R.drawable.ic_broken_image),
        contentScale = ContentScale.Crop
    )

    AsyncImage(
        model = data,
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
    itemRun: RunData,
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

@Preview(showBackground = true)
@Composable
private fun InfoRunPreview(){
    InfoRun(
        itemRun = RunData(
            id = 1,
            avgSpeedInMeters = 100F,
            caloriesBurned = 10F,
            distanceInMeters = 1000F,
            listPolyLineEncode = emptyList(),
            pathImgRun = null,
            timeRunInMillis = 10,
            mapConfig = MapConfig(),
            timestamp = 10
        ),
        metricType = MetricType.Meters
    )
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

@Preview(
    showBackground = true
)
@Composable
fun DetailsRunTextPreview() {
    DetailsRunText(text = "Este es un texto de prueba")
}