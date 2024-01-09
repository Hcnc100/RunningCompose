package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.getGrayColor
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapColor
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMapWeight
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig


@Composable
fun MapSettings(
    orientation: Int,
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    changeColorMap: (Color) -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
) {
    val (isDialogShow, changeVisibleDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        TitleConfig(text = stringResource(R.string.title_config_map))
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                MapSettingsConfigPortrait(
                    mapConfig = mapConfig,
                    changeWeight = changeWeight,
                    changeStyleMap = changeStyleMap,
                    showDialogColor = { changeVisibleDialog(true) },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

            else -> {
                MapSettingsConfigLandscape(
                    mapConfig = mapConfig,
                    changeWeight = changeWeight,
                    changeStyleMap = changeStyleMap,
                    showDialogColor = { changeVisibleDialog(true) },
                    modifier = Modifier.padding(horizontal = 10.dp)

                )
            }
        }
    }
    if (isDialogShow) {
        DialogColorPicker(
            hiddenDialog = { changeVisibleDialog(false) },
            changeColor = changeColorMap
        )
    }
}

@Composable
fun MapSettingsConfigLandscape(
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    showDialogColor: () -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
    modifier: Modifier=Modifier
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

@Composable
private fun MapSettingsConfigPortrait(
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    showDialogColor: () -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
    modifier: Modifier= Modifier
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






