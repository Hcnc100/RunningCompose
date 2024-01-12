package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.share.empty.LottieContainerForever


@Composable
fun DescriptionScreen(
    modifier: Modifier = Modifier
) {
    ContainerItemIntro(
        modifier = modifier
    ) {

        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.h5,
            color = Color.White,
        )

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_run),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = stringResource(R.string.description_map),
            color = Color.White
        )

    }
}

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier
) {
    ContainerItemIntro(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.title_location_permission),
            style = MaterialTheme.typography.h5,
            color = Color.White,
        )

        LottieContainerForever(
            animation = R.raw.location,
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = stringResource(R.string.title_description_permission),
            color = Color.White
        )

    }
}

@Composable
fun NotifyScreen(
    modifier: Modifier = Modifier
) {
    ContainerItemIntro(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.title_notify_permission),
            style = MaterialTheme.typography.h5,
            color = Color.White,
        )

        LottieContainerForever(
            animation = R.raw.notify,
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = stringResource(R.string.title_notify_description),
            color = Color.White
        )

    }
}


@SimplePreview
@Composable
fun DescriptionScreenPreview() {
    DescriptionScreen()
}

@SimplePreview
@Composable
fun LocationScreenPreview() {
    LocationScreen()
}

@SimplePreview
@Composable
fun NotifyScreenPreview() {
    NotifyScreen()
}
