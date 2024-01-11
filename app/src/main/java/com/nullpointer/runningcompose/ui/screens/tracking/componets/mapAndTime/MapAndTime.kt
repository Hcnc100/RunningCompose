package com.nullpointer.runningcompose.ui.screens.tracking.componets.mapAndTime

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.preview.config.OrientationPreviews
import com.nullpointer.runningcompose.ui.preview.states.TrackingStateProvider
import com.nullpointer.runningcompose.ui.screens.tracking.TrackingActions
import com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices.ButtonsServicesLandscape
import com.nullpointer.runningcompose.ui.screens.tracking.componets.buttonsServices.ButtonsServicesPortrait
import com.nullpointer.runningcompose.ui.screens.tracking.componets.map.MapTracking


@Composable
fun MapAndTime(
    timeRun: Long,
    mapViewState: MapView,
    lastLocation: LatLng?,
    drawPolyData: DrawPolyData,
    servicesState: TrackingState,
    modifier: Modifier = Modifier,
    actionServices: (TrackingActions) -> Unit,
    orientation: Int = LocalConfiguration.current.orientation,
) {

    val textRun = remember(timeRun) {
        timeRun.toFullFormatTime(true)
    }

    when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> {
            Column(modifier = modifier.fillMaxSize()) {
                MapTracking(
                    drawPolyData = drawPolyData,
                    lastLocation = lastLocation,
                    modifier = Modifier.weight(.75f),
                    mapViewState = mapViewState
                )
                TimeRunContainer(textTimeRun = textRun) {
                    ButtonsServicesPortrait(
                        actionServices = actionServices,
                        servicesState = servicesState
                    )
                }
            }
        }

        Configuration.ORIENTATION_LANDSCAPE -> {
            Box(modifier = Modifier.fillMaxSize()) {
                MapTracking(
                    lastLocation = lastLocation,
                    drawPolyData = drawPolyData,
                    mapViewState = mapViewState,
                    modifier = Modifier.fillMaxSize()
                )

                TextTimeRun(
                    textTimeRun = textRun,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(MaterialTheme.colors.primary)
                        .padding(5.dp)
                )

                ButtonsServicesLandscape(
                    actionServices = actionServices,
                    servicesState = servicesState,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                )
            }
        }
    }
}


@OrientationPreviews
@Composable
private fun MapAndTimePreview(
    @PreviewParameter(TrackingStateProvider::class)
    state: TrackingState
) {
    MapAndTime(
        timeRun = 100000,
        actionServices = {},
        lastLocation = null,
        servicesState = state,
        drawPolyData = DrawPolyData(),
        mapViewState = MapView(LocalContext.current)
    )
}