package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig


private val listMaps = MapStyle.values().associateBy({ it }, { it.string })
@Composable
 fun SelectMapStyle(
    currentStyle: MapStyle,
    changeStyleMap: (MapStyle) -> Unit,
) {
    SelectOptionConfig(
        titleField = stringResource(R.string.title_map_style),
        textCurrentSelected = stringResource(id = currentStyle.string),
        listItemsAndNames = listMaps,
        onChange = changeStyleMap,
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun SelectMapStylePreview() {
    SelectMapStyle(
        currentStyle = MapStyle.ASSASSINS,
        changeStyleMap = {},
    )
}
