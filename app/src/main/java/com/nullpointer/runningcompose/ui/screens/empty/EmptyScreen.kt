package com.nullpointer.runningcompose.ui.screens.empty

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.*

@Composable
fun EmptyScreen(
    @RawRes
    animation: Int,
    textEmpty: String,
) {
    Column {
        LottieContainer(
            modifier = Modifier
                .weight(.8f)
                .fillMaxWidth(),
            animation = animation
        )
        Box(
            modifier = Modifier
                .weight(.2f)
                .fillMaxWidth()
        ) {
            Text(
                text = textEmpty,
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.TopCenter),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LottieContainer(modifier: Modifier, @RawRes animation: Int) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier,
    )
}
