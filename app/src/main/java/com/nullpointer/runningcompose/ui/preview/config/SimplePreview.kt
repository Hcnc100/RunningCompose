package com.nullpointer.runningcompose.ui.preview.config

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Simple Preview",
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
annotation class SimplePreview


@Preview(
    name = "Simple Preview dark",
    showBackground = true,
    backgroundColor = 0x000000
)
annotation class SimplePreviewBlack