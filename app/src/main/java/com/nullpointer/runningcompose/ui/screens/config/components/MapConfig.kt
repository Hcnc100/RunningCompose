package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig
import timber.log.Timber


@Composable
fun MapSettings(
    orientation: Int,
    configMap: MapConfig?,
    changeWeight: (Int) -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
    changeColorMap: (Color) -> Unit,
) {
    val (isDialogShow, changeVisibleDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        TitleConfig(text = stringResource(R.string.title_config_map))
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                configMap?.let { mapConfig ->
                    MapFromConfig(mapConfig = configMap)
                    SelectMapStyle(currentStyle = mapConfig.style, changeStyleMap = changeStyleMap)
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectMapWeight(currentWeightMap = mapConfig.weight,
                        changeWeight = changeWeight)
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    SelectMapColor(
                        currentColor = mapConfig.color,
                        showDialogColor = { changeVisibleDialog(true) }
                    )
                }
            }
            else -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    configMap?.let { mapConfig ->
                        MapFromConfig(mapConfig = configMap, modifier = Modifier.weight(.5f))
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
    }
    if (isDialogShow)
        DialogColorPicker(
            hiddenDialog = { changeVisibleDialog(false) },
            changeColor = changeColorMap)
}


@Composable
private fun SelectMapWeight(
    currentWeightMap: Int,
    changeWeight: (Int) -> Unit,
) {
    val listWeight = remember { listOf(3, 4, 5, 6, 7, 8, 9, 10) }
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
        Text(text = "Color de la linea",
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
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                shape = RoundedCornerShape(5.dp))
            .padding(2.dp)
            .background(currentColor)
            .clickable {
                showDialogColor()
            }
        )
    }

}


