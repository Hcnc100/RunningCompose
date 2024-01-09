package com.nullpointer.runningcompose.ui.screens.runs.componets

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
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun

@Composable
fun ContainerPermission(
    isFirstRequestPermission: Boolean,
    actionRunScreen: (ActionRun, RunData?) -> Unit,
) {
    val textExplanation = if (isFirstRequestPermission){
        stringResource(id = R.string.need_permissions_tracking)
    }else{
        stringResource(id = R.string.setting_permissions_tracking)
    }

    val buttonText = stringResource(id = R.string.action_accept)

    val actionPermission = if (isFirstRequestPermission){
        { actionRunScreen(ActionRun.LAUNCH_PERMISSION, null) }
    }else{
        { actionRunScreen(ActionRun.OPEN_SETTING, null) }
    }

    PermissionBox(
        textExplanation = textExplanation,
        buttonText = buttonText,
        actionPermission = actionPermission
    )
}

@Composable
private fun PermissionBox(
    textExplanation: String,
    buttonText: String,
    actionPermission: () -> Unit
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
            LottieContainer(modifier = Modifier.size(250.dp), animation = R.raw.location)
            Text(text = textExplanation, textAlign = TextAlign.Center)
            Button(onClick = actionPermission) {
                Text(text = buttonText)
            }
        }
    }
}

