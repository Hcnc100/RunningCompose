package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.*
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run

@Composable
fun MapRunItem(
    itemRun: Run,
    modifier: Modifier = Modifier,
    showCenterButton: Boolean,
) {

    val camera = rememberCameraPositionState()

    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false
        )
    }
    val properties = remember {
        MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                context,
                itemRun.mapConfig.style.styleRawRes
            ),
        )
    }

    val listPointsDecode = remember {
        itemRun.listPolyLineEncode.map {
            PolyUtil.decode(it)
        }
    }

    val bounds = remember {
        LatLngBounds.builder().let { builder ->
            listPointsDecode.forEach {
                it.forEach(builder::include)
            }
            builder.build()
        }
    }

    Card(shape = RoundedCornerShape(10.dp), modifier = modifier) {
        Box {
            GoogleMap(
                cameraPositionState = camera,
                uiSettings = uiSettings,
                modifier = Modifier.fillMaxSize(),
                properties = properties,
                onMapLoaded = {
                    camera.move(CameraUpdateFactory.newLatLngBounds(bounds, 10))
                }
            ) {
                listPointsDecode.forEach {
                    Polyline(
                        points = it,
                        color = itemRun.mapConfig.color,
                        width = itemRun.mapConfig.weight.toFloat())
                }
            }
            if (showCenterButton)
                FloatingActionButton(onClick = {
                    camera.move(CameraUpdateFactory.newLatLngBounds(bounds,
                        10))
                },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                        .align(Alignment.BottomEnd)) {
                    Icon(painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = stringResource(R.string.description_location_center))
                }
        }

    }

}