package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
) {
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
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MapFromConfig(
    mapConfig: MapConfig,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(
            myLocationButtonEnabled = false,
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false
        ))
    }
    var properties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                    mapConfig.style.styleRawRes),
            )
        )
    }
    val listPoints by remember {
        mutableStateOf(listOf(
            LatLng(53.3477, -6.2597),
            LatLng(51.5008, -0.1224),
            LatLng(48.8567, 2.3508),
            LatLng(52.5166, 13.3833),
        ))
    }
    val bounds by remember {
        mutableStateOf(LatLngBounds.builder().let {
            listPoints.forEach(it::include)
            it.build()
        })
    }
    val cameraPositionState = rememberCameraPositionState()
    LaunchedEffect(key1 = mapConfig) {
        properties = properties.copy(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context,
                mapConfig.style.styleRawRes)
        )
    }

    GoogleMap(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(vertical = 10.dp, horizontal = 20.dp),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMapLoaded = {
            cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 10))
        }
    ) {
        Polyline(points = listPoints, width = mapConfig.weight.toFloat())
    }
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


