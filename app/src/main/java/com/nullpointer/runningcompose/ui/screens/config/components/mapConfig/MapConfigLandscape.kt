package com.nullpointer.runningcompose.ui.screens.config.components.mapConfig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.MapFromConfig
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapColor
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapWeight

@Composable
fun MapSettingsConfigLandscape(
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    showDialogColor: () -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MapFromConfig(mapConfig = mapConfig, modifier = Modifier.weight(.5f))
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(.5f),
        ) {
            SelectMapStyle(
                currentStyle = mapConfig.style,
                changeStyleMap = changeStyleMap,
            )
            SelectMapWeight(
                currentWeightMap = mapConfig.weight,
                changeWeight = changeWeight
            )
            SelectMapColor(
                currentColor = mapConfig.color,
                showDialogColor = showDialogColor
            )
        }
    }
}

@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 720,
    heightDp = 360
)
@Composable
fun MapSettingsConfigLandscapePreview() {
    MapSettingsConfigLandscape(
        mapConfig = MapConfig(),
        showDialogColor = {},
        changeWeight = {},
        changeStyleMap = {}
    )
}
