package com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogCancel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.share.empty.LottieContainerForever

@Composable
fun AnimationCancelRun() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_dialog_cancel_tracking),
            style = MaterialTheme.typography.h6
        )
        LottieContainerForever(
            animation = R.raw.clear,
            modifier = Modifier.size(150.dp)
        )
        Text(
            stringResource(R.string.message_info_cancel_tracking),
            style = MaterialTheme.typography.body1
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@SimplePreview
@Composable
private fun AnimationCancelPreview() {
    AnimationCancelRun()
}
