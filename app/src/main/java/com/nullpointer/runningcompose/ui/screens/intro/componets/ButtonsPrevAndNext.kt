package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            Text(text = "Prev", color = Color.White)
        }
        if (!isLastPage) {
            TextButton(
                onClick = { actionIntro(IntroActions.NEXT) },
            ) {
                Text(text = "Next", color = Color.White)
            }
        }
        if (isLastPage) {
            TextButton(
                onClick = { actionIntro(IntroActions.START) },
            ) {
                Text(text = "Iniciar", color = Color.White)
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
        modifier = Modifier.background(MaterialTheme.colors.primary)
    )
}