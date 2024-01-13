package com.nullpointer.runningcompose.ui.states

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitSnapshot
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.services.TrackingServices
import com.nullpointer.runningcompose.ui.screens.config.components.includeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Stable
class TrackingScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val scope: CoroutineScope
) : SimpleScreenState(context, scaffoldState) {


    fun startOrResumeTracking() {
        TrackingServices.startServicesOrResume(context)
    }

    fun pauseTracking() {
        TrackingServices.pauseServices(context)
    }

    fun finishTracking() {
        TrackingServices.finishServices(context)
    }

    suspend fun getSnapshotMap(
        mapView: MapView,
        drawPolyData: DrawPolyData
    ): Bitmap? = withContext(Dispatchers.Main) {

        val map = mapView.awaitMap()
        val bounds = LatLngBounds.builder().apply {
            drawPolyData.listLocation.forEach { list ->
                includeAll(list)
            }
        }.build()
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(
            /* bounds = */ bounds,
            /* width = */500,
            /* height = */500,
            /* padding = */10,
        )
        map.moveCamera(cameraUpdate)
        return@withContext map.awaitSnapshot()
    }
}


@Composable
fun rememberTrackingScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, coroutineScope) {
    TrackingScreenState(
        context = context,
        scaffoldState = scaffoldState,
        scope = coroutineScope
    )
}
