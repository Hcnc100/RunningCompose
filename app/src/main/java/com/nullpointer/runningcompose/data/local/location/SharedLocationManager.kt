package com.nullpointer.runningcompose.data.local.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import timber.log.Timber

class SharedLocationManager constructor(
    context: Context,
    externalScope: CoroutineScope,
) {

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5000L
        private const val FASTEST_LOCATION_INTERVAL = 2000L
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest = LocationRequest.create().apply {
        interval = LOCATION_UPDATE_INTERVAL
        fastestInterval = FASTEST_LOCATION_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    @SuppressLint("MissingPermission")
    private val _lastLocation = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                Timber.d("New location: ${result.lastLocation.toText()}")
                trySend(result.lastLocation.toLatLng())
            }
        }
        Timber.d("Starting location updates")

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e) // in case of exception, close the Flow
        }

        awaitClose {
            Timber.d("Stopping location updates")
            fusedLocationClient.removeLocationUpdates(callback) // clean up when Flow collection ends
        }
    }.shareIn(
        externalScope,
        replay = 0,
        started = SharingStarted.WhileSubscribed()
    )

    fun lastLocationFlow(): Flow<LatLng> {
        return _lastLocation
    }
}

private fun Location.toText(): String {
    return "lat:${this.latitude} log${this.longitude}"
}

private fun Location.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}
