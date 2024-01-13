package com.nullpointer.runningcompose.ui.screens.tracking.componets

import android.support.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.actions.PermissionActions
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.share.empty.LottieContainerForever

@Composable
fun ContainerPermissionLocation(
    isFirstRequestPermission: Boolean,
    permissionAction: (PermissionActions) -> Unit,

) {
    val textExplanation = if (isFirstRequestPermission){
        stringResource(id = R.string.need_permissions_tracking)
    }else{
        stringResource(id = R.string.setting_permissions_tracking)
    }

    val buttonText = stringResource(id = R.string.action_accept)

    val actionPermission = if (isFirstRequestPermission){
        { permissionAction(PermissionActions.LAUNCH_LOCATION_PERMISSION) }
    }else{
        { permissionAction(PermissionActions.OPEN_SETTING) }
    }

    PermissionBox(
        textExplanation = textExplanation,
        buttonText = buttonText,
        actionPermission = actionPermission,
        animation = R.raw.location
    )
}

@Composable
fun ContainerPermissionNotify(
    isFirstRequestPermission: Boolean,
    permissionAction: (PermissionActions) -> Unit,

    ) {
    val textExplanation = if (isFirstRequestPermission){
        stringResource(id = R.string.need_permissions_notify)
    }else{
        stringResource(id = R.string.setting_permissions_notify)
    }

    val buttonText = stringResource(id = R.string.action_accept)

    val actionPermission = if (isFirstRequestPermission){
        { permissionAction(PermissionActions.LAUNCH_NOTIFICATION_PERMISSION) }
    }else{
        { permissionAction(PermissionActions.OPEN_SETTING) }
    }

    PermissionBox(
        textExplanation = textExplanation,
        buttonText = buttonText,
        actionPermission = actionPermission,
        animation = R.raw.notify
    )
}


@Composable
private fun PermissionBox(
    buttonText: String,
    textExplanation: String,
    actionPermission: () -> Unit,
    @RawRes
    animation: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LottieContainerForever(modifier = Modifier.size(250.dp), animation = animation)
            Text(text = textExplanation, textAlign = TextAlign.Center)
            Button(onClick = actionPermission) {
                Text(text = buttonText)
            }
        }
    }
}

@SimplePreview
@Composable
private fun PermissionLocationPreview() {
    ContainerPermissionLocation(
        isFirstRequestPermission = true,
        permissionAction = {}
    )
}

@SimplePreview
@Composable
private fun PermissionNotifyPreview() {
    ContainerPermissionNotify(
        isFirstRequestPermission = true,
        permissionAction = {}
    )
}
