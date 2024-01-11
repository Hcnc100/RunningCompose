package com.nullpointer.runningcompose.ui.screens.tracking.componets.dialogs.dialogSaved

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.share.empty.LottieContainerForever


@Composable
fun AnimationSavingRun() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.message_info_saved_tracking),
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        LottieContainerForever(
            modifier = Modifier.size(200.dp),
            animation = R.raw.map
        )
    }
}

@ThemePreviews
@Composable
fun AnimationSavingRunPreview() {
    AnimationSavingRun()
}