package com.nullpointer.runningcompose.ui.share.empty

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nullpointer.runningcompose.R

@Composable
fun LottieContainerForever(
    @RawRes animation: Int,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )
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

@Preview
@Composable
private fun LottieContainerForeverPreview() {
    LottieContainerForever(
        animation = R.raw.empty1
    )
}