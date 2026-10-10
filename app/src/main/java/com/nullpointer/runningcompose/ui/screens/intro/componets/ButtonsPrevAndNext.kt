package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.actions.IntroActions
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun ButtonsPrevAndNext(
    isFirstPage: Boolean,
    isLastPage: Boolean,
    modifier: Modifier = Modifier,
    actionIntro: (IntroActions) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextButton(
            onClick = { actionIntro(IntroActions.PREV) },
            enabled = !isFirstPage
        ) {
            Text(text = stringResource(R.string.title_prev_button))
        }
        if (!isLastPage) {
            Button(
                onClick = { actionIntro(IntroActions.NEXT) },
            ) {
                Text(text = stringResource(R.string.title_next_button))
            }
        }
        if (isLastPage) {
            Button(
                onClick = { actionIntro(IntroActions.START) },
            ) {
                Text(text = stringResource(R.string.title_start_button))
            }
        }

    }
}

@SimplePreview
@Composable
fun ButtonsPrevAndNextPreview() {
    ButtonsPrevAndNext(
        isFirstPage = true,
        isLastPage = true,
        actionIntro = {},
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    )
}
