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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.getGrayColor
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    MapFromConfig(mapConfig = mapConfig)
                    SelectMapStyle(currentStyle = mapConfig.style, changeStyleMap = changeStyleMap)
                    SelectMapWeight(
                        currentWeightMap = mapConfig.weight,
                        changeWeight = changeWeight
                    )
                    SelectMapColor(
                        currentColor = mapConfig.color,
                        showDialogColor = { changeVisibleDialog(true) }
                    )
                }

            }
            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MapFromConfig(mapConfig = mapConfig, modifier = Modifier.weight(.5f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(.5f),
                    ) {
                        SelectMapStyle(currentStyle = mapConfig.style,
                            changeStyleMap = changeStyleMap)
                        Spacer(modifier = Modifier.height(10.dp))
                        SelectMapWeight(currentWeightMap = mapConfig.weight,
                            changeWeight = changeWeight)
                        Spacer(modifier = Modifier.height(10.dp))
                        SelectMapColor(
                            currentColor = mapConfig.color,
                            showDialogColor = { changeVisibleDialog(true) }
                        )
                    }
                }
            }
        }
    }
    if (isDialogShow)
        DialogColorPicker(
            hiddenDialog = { changeVisibleDialog(false) },
            changeColor = changeColorMap
        )
}


@Composable
private fun SelectMapWeight(
    currentWeightMap: Int,
    changeWeight: (Int) -> Unit,
) {
    val listWeight = listOf(3, 4, 5, 6, 7, 8, 9, 10)
    SelectOptionConfig(
        textField = stringResource(R.string.title_weight_map_line),
        selected = currentWeightMap.toString(),
        listItems = listWeight,
        onChange = changeWeight)
}

@Composable
private fun SelectMapStyle(
    currentStyle: MapStyle,
    changeStyleMap: (MapStyle) -> Unit,
) {
    val listMaps =  MapStyle.values().toList()
    SelectOptionConfig(
        textField = stringResource(R.string.title_map_style),
        selected = stringResource(id = currentStyle.string),
        listItems = listMaps,
        listNamed = listMaps.map { stringResource(id = it.string) },
        onChange = changeStyleMap)
}

@Composable
fun SelectMapColor(
    currentColor: Color,
    modifier: Modifier = Modifier,
    showDialogColor: () -> Unit,
) {

    Row(modifier = modifier
        .padding(horizontal = 10.dp)
        .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Text(text = stringResource(R.string.title_color_line),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(.5f)
        )
        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier
            .weight(.5f)
            .fillMaxHeight()
            .border(
                width = 2.dp,
                color = getGrayColor(),
                shape = RoundedCornerShape(5.dp)
            )
            .padding(2.dp)
            .background(currentColor)
            .clickable { showDialogColor() }
        )
    }

}


