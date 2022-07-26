package com.nullpointer.runningcompose.ui.screens.runs.componets

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.ktx.addPolyline
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.ui.screens.config.components.includeAll
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle
import timber.log.Timber

@Composable
fun MapRunItem(
    itemRun: Run,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val mapState = rememberMapWithLifecycle()
    val listDecode= remember {
        itemRun.listPolyLineEncode.map {
            PolyUtil.decode(it)
        }
    }
    val bound= remember {
         LatLngBounds.builder().apply {
            listDecode.forEach {
                includeAll(it)
            }
        }.build()
    }

    Box(modifier = modifier) {
        AndroidView(factory = { mapState }, update = { mapView ->
            Timber.d("Update map")
            mapView.getMapAsync {map->
                map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        context,
                        itemRun.mapConfig.style.styleRawRes
                    )
                )
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bound,50))
                listDecode.forEach {
                    map.addPolyline {
                       color(itemRun.mapConfig.colorValue)
                       width(itemRun.mapConfig.weight.toFloat())
                       addAll(it)
                    }
                }

            }
        })

        FloatingActionButton(
            modifier=Modifier.padding(10.dp).size(45.dp).align(Alignment.BottomEnd),
            onClick = {
            mapState.getMapAsync {map->
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bound,50))
            }
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription =null )
        }
    }

}