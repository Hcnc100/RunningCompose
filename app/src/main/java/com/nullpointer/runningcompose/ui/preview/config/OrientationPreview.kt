package com.nullpointer.runningcompose.ui.preview.config

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
@Preview(
    name = "Portrait Mode",
    showBackground = true,
    device = Devices.PIXEL_4
)
annotation class OrientationPreviews


@Preview(
    name = "Landscape Mode",
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class LandscapePreview


@Preview(
    name = "Portrait Mode",
    showBackground = true,
    device = Devices.PIXEL_4,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class PortraitPreview
