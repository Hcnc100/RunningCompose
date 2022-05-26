package com.nullpointer.runningcompose.core.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object Utility {

    fun calculatePolylineLength(polyline: List<LatLng>): Float {
        var distance = 0F
        for (i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]

            val result = FloatArray(1)

            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
}