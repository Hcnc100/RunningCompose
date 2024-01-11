package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import com.nullpointer.runningcompose.core.utils.getGrayColor
import com.nullpointer.runningcompose.core.utils.isSuccess
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews

@Composable
fun ImageRun(
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
        colorFilter = if (painter.isSuccess) null else ColorFilter.tint(getGrayColor()),
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
    )
}


@ThemePreviews
@Composable
private fun ImageRunPreview() {
    ImageRun(
        pathImage = null,
        modifier = Modifier.size(150.dp)
    )
}
