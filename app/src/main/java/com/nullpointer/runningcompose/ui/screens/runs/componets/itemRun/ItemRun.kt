package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.data.RunData
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
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment =  Alignment.CenterVertically
        ) {
            // * waiting to take snapshot for maps compose
            ImageRun(
                pathImage = itemRunEntity.pathImgRun,
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
    pathImage: String?,
    modifier: Modifier = Modifier
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(pathImage)
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



