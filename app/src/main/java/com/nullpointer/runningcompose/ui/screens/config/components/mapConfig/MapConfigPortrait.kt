package com.nullpointer.runningcompose.ui.screens.config.components.mapConfig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.preview.config.PortraitPreview
import com.nullpointer.runningcompose.ui.screens.config.components.MapFromConfig
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapColor
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapWeight

@Composable
fun MapSettingsConfigPortrait(
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    showDialogColor: () -> Unit,
    modifier: Modifier = Modifier,
    changeStyleMap: (MapStyle) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        MapFromConfig(mapConfig = mapConfig)
        SelectMapStyle(
            currentStyle = mapConfig.style,
            changeStyleMap = changeStyleMap
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

@PortraitPreview
@Composable
fun MapSettingsConfigPortraitPreview() {
    MapSettingsConfigPortrait(
        mapConfig = MapConfig(),
        changeWeight = {},
        showDialogColor = { },
        changeStyleMap = {}
    )
}

