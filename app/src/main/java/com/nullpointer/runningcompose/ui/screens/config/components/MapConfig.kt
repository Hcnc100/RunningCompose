package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig


@Composable
fun MapSettings(orientation: Int, configMap: MapConfig?) {
    Column {
        TitleConfig(text = stringResource(R.string.title_config_map))
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                MapFromConfig()
                configMap?.let { mapConfig ->
                    SelectMapStyle(currentStyle = mapConfig.style, changeStyleMap = {})
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectMapWeight(currentWeightMap = mapConfig.weight, changeWeight = {})
                }
            }
            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MapFromConfig(modifier = Modifier.weight(.5f))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(.5f),
                    ) {
                        configMap?.let { mapConfig ->
                            SelectMapStyle(currentStyle = mapConfig.style, changeStyleMap = {})
                            Spacer(modifier = Modifier.height(10.dp))
                            SelectMapWeight(currentWeightMap = mapConfig.weight, changeWeight = {})
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MapFromConfig(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(2f)
        .padding(vertical = 10.dp, horizontal = 20.dp)
        .background(
            Color.Gray))
}

@Composable
private fun SelectMapWeight(
    currentWeightMap: Int,
    changeWeight: (Int) -> Unit,
) {
    val listWeight = remember { listOf(1, 2, 3, 4, 5, 6, 7) }
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
    val listMaps = remember { MapStyle.values().toList() }
    SelectOptionConfig(
        textField = stringResource(R.string.title_map_style),
        selected = stringResource(id = currentStyle.string),
        listItems = listMaps,
        listNamed = listMaps.map { stringResource(id = it.string) },
        onChange = changeStyleMap)
}


