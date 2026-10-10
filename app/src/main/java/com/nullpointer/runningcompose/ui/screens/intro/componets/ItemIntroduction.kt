package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.actions.PermissionActions
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
            text = stringResource(R.string.text_welcome),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_run),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = stringResource(R.string.description_map),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun LocationScreen(
    modifier: Modifier = Modifier,
    permissionAction: (PermissionActions) -> Unit,
    isFirstLocationPermission: Boolean
) {

    val textButtonPermission = if (isFirstLocationPermission) {
        stringResource(R.string.title_grant_permission)
    } else {
        stringResource(R.string.title_open_setting)
    }

    ContainerItemIntro(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.title_location_permission),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        LottieContainerForever(
            animation = R.raw.location,
            modifier = Modifier.size(180.dp)
        )

        Button(
            onClick = { permissionAction( PermissionActions.LAUNCH_LOCATION_PERMISSION)}) {
            Text(text = textButtonPermission)
        }

        Text(
            text = stringResource(R.string.title_description_permission),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )

    }
}

@Composable
fun NotifyScreen(
    modifier: Modifier = Modifier,
    permissionAction: (PermissionActions) -> Unit,
    isFirstNotificationPermission: Boolean
) {

    val textButtonPermission = if (isFirstNotificationPermission) {
        stringResource(R.string.title_grant_permission)
    } else {
        stringResource(R.string.title_open_setting)
    }


    ContainerItemIntro(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.title_notify_permission),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )

        LottieContainerForever(
            animation = R.raw.notify,
            modifier = Modifier.size(180.dp)
        )

        Button(
            onClick = {permissionAction( PermissionActions.LAUNCH_NOTIFICATION_PERMISSION)}
        ) {
            Text(text =textButtonPermission)
        }

        Text(
            text = stringResource(R.string.title_notify_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
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
    LocationScreen(
        permissionAction = {},
        isFirstLocationPermission = false
    )
}

@SimplePreview
@Composable
fun NotifyScreenPreview() {
    NotifyScreen(
        permissionAction = {},
        isFirstNotificationPermission = false
    )
}
