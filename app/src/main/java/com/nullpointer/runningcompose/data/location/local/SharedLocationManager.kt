package com.nullpointer.runningcompose.data.location.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn
import timber.log.Timber

/**
 * A manager for shared location updates.
 *
 * @property context The application context.
 * @property externalScope The external coroutine scope.
 */
class SharedLocationManager(
    context: Context,
    externalScope: CoroutineScope,
) {

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5000L

        //        private const val FASTEST_LOCATION_INTERVAL = 2000L
        private const val MINIMAL_DISTANCE_IN_METERS = 0F
    }

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATE_INTERVAL).apply {
            setMinUpdateDistanceMeters(MINIMAL_DISTANCE_IN_METERS)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
//    private val locationRequest = LocationRequest.create().apply {
//        interval = LOCATION_UPDATE_INTERVAL
//        fastestInterval = FASTEST_LOCATION_INTERVAL
//        priority = Priority.PRIORITY_HIGH_ACCURACY
//    }

    /**
     * Starts location updates when collected and stops when the collection stops.
     */
    @SuppressLint("MissingPermission")
    private val _lastLocation = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { trySend(it.toLatLng()) }
            }
        }
        Timber.e("Starting location updates")

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            callback,
            Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e) // in case of exception, close the Flow
        }

        awaitClose {
            Timber.e("Stopping location updates")
            fusedLocationClient.removeLocationUpdates(callback) // clean up when Flow collection ends
        }
    }.shareIn(
        externalScope,
        replay = 1,
        started = SharingStarted.WhileSubscribed()
    )

    /**
     * Returns a flow of the last location.
     *
     * @return The flow of the last location.
     */
    fun lastLocationFlow(): Flow<LatLng> {
        return _lastLocation
    }
}

/**
 * Converts a Location object to a LatLng object.
 *
 * @return The LatLng object.
 */
private fun Location.toLatLng(): LatLng {
    return LatLng(this.latitude, this.longitude)
}