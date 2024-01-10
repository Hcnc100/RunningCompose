package com.nullpointer.runningcompose.ui.screens.details.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.PolyUtil
import com.google.maps.android.ktx.addPolyline
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.ui.screens.config.components.includeAll
import com.nullpointer.runningcompose.ui.screens.config.components.rememberMapWithLifecycle

@Composable
fun MapRunItem(
    itemRunEntity: RunData,
    alignmentButton: Alignment,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {

    val mapState = rememberMapWithLifecycle()
    val listDecode = remember {
        itemRunEntity.listPolyLineEncode.map {
            PolyUtil.decode(it)
        }
    }
    val bound = remember {
        LatLngBounds.builder().apply {
            listDecode.forEach {
                includeAll(it)
            }
        }.build()
    }

    Box(
        modifier = modifier
    ) {
        AndroidView(
            factory = { mapState },
            update = { mapView ->
                mapView.getMapAsync { map ->
                    map.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                            context,
                            itemRunEntity.mapConfig.style.styleRawRes
                        )
                    )
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bound, 50))
                    listDecode.forEach {
                        map.addPolyline {
                            color(itemRunEntity.mapConfig.colorValue)
                            width(itemRunEntity.mapConfig.weight.toFloat())
                            addAll(it)
                        }
                    }

                }
            })

        ButtonCenterLocation(
            modifier = Modifier.align(alignmentButton)
        ) {
            mapState.getMapAsync { map ->
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bound, 50))
            }
        }
    }

}