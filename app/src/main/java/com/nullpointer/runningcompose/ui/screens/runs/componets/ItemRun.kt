package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRun(
    itemRun: Run,
    isSelectEnable: Boolean,
    actionClick: (Run) -> Unit,
    actionSelect: (Run) -> Unit,
) {

    Card(
        modifier = Modifier
            .padding(3.dp)
            .combinedClickable(
                onClick = { if (isSelectEnable) actionSelect(itemRun) else actionClick(itemRun) },
                onLongClick = { actionSelect(itemRun) }
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = if (itemRun.isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    ) {
        Row(modifier = Modifier
            .padding(10.dp)
            .height(150.dp)) {

            // ! this is not perfomance
//            MapRunItem(
//                itemRun = itemRun,
//                modifier = Modifier
//                    .weight(.5f)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(10.dp)
//                    )
//            )
            // * waiting to take snapshot for maps compose
            Box(modifier = Modifier
                .weight(.5f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.primary)) {

                AsyncImage(
                    model =  R.drawable.ic_run,
                    placeholder = painterResource(id = R.drawable.ic_run),
                    error = painterResource(id = R.drawable.ic_error_img),
                    contentDescription = stringResource(R.string.description_current_run_img),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().padding(20.dp)
                )
            }


            Spacer(modifier = Modifier.width(20.dp))
            InfoRun(itemRun = itemRun,
                modifier = Modifier.weight(.5f),
                dataComplete = false,
                isMiniTitle = true)
        }
    }
}


@Composable
fun InfoRun(
    itemRun: Run,
    modifier: Modifier = Modifier,
    dataComplete: Boolean,
    isMiniTitle: Boolean,
) {
    Row(modifier = modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth(),
        horizontalArrangement = if (isMiniTitle) Arrangement.SpaceBetween else Arrangement.SpaceEvenly) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = stringResource(R.string.item_title_date), isMiniTitle)
            TextMiniTitle(text = stringResource(R.string.item_title_hour), isMiniTitle)
            TextMiniTitle(text = stringResource(R.string.item_title_duration), isMiniTitle)
            TextMiniTitle(text = stringResource(R.string.item_title_distance), isMiniTitle)
            TextMiniTitle(text = stringResource(R.string.item_title_speed), isMiniTitle)
            TextMiniTitle(text = stringResource(R.string.item_title_calories), isMiniTitle)
        }
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = itemRun.timestamp.toDateFormat(), isMiniTitle)
            TextMiniTitle(text = itemRun.timestamp.toDateOnlyTime(LocalContext.current),
                isMiniTitle)
            TextMiniTitle(text = itemRun.timeRunInMillis.toFullFormatTime(dataComplete),
                isMiniTitle)
            TextMiniTitle(text = itemRun.distanceInMeters.toMeters(true), isMiniTitle)
            TextMiniTitle(text = itemRun.avgSpeedInMeters.toAVGSpeed(true), isMiniTitle)
            TextMiniTitle(text = itemRun.caloriesBurned.toCaloriesBurned(true), isMiniTitle)
        }
    }
}

@Composable
private fun TextMiniTitle(
    text: String,
    isMiniTitle: Boolean,
) {
    Text(text = text,
        style = if (isMiniTitle) MaterialTheme.typography.caption else MaterialTheme.typography.body1,
        fontSize = if (isMiniTitle) 14.sp else TextUnit.Unspecified,
        maxLines = 1,
        overflow = if (isMiniTitle) TextOverflow.Ellipsis else TextOverflow.Clip)
}