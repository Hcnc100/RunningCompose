package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemRun(
    itemRun: Run,
    actionClick: (Run) -> Unit,
) {
    Card(
        modifier = Modifier.padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { actionClick(itemRun) }
    ) {
        Row(modifier = Modifier
            .padding(10.dp)
            .height(150.dp)) {
            AsyncImage(
                model = "https://picsum.photos/200/300",
                placeholder = painterResource(id = R.drawable.ic_run),
                error = painterResource(id = R.drawable.ic_error_img),
                contentDescription = stringResource(R.string.description_current_run_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(.5f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(20.dp))
            InfoRun(itemRun = itemRun, modifier = Modifier.weight(.5f))
        }
    }
}


@Composable
fun InfoRun(itemRun: Run, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(vertical = 10.dp)) {
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = "Fecha:")
            TextMiniTitle(text = "Duracion:")
            TextMiniTitle(text = "Distancia:")
            TextMiniTitle(text = "Velocidad:")
            TextMiniTitle(text = "Calorias:")
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextMiniTitle(text = itemRun.timestamp.toDateFormat())
            TextMiniTitle(text = itemRun.timeRunInMillis.toFullFormatTime(false))
            TextMiniTitle(text = itemRun.distance.toMeters(true))
            TextMiniTitle(text = itemRun.avgSpeed.toAVGSpeed(true))
            TextMiniTitle(text = itemRun.caloriesBurned.toCaloriesBurned(true))
        }
    }
}

@Composable
fun TextMiniTitle(
    text: String,
) {
    Text(text = text,
        style = MaterialTheme.typography.caption,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis)
}