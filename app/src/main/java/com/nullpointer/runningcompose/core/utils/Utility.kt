package com.nullpointer.runningcompose.core.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

object Utility {

    /**
     * This function calculates the total length of a polyline.
     *
     * @param polyline A list of LatLng objects representing the polyline.
     * @return The total length of the polyline in meters.
     */
    fun calculatePolylineLength(polyline: List<LatLng>): Float {
        // Initialize a variable to store the total distance.
        var distance = 0.0

        // Iterate over each pair of consecutive points in the polyline.
        for ((pos1, pos2) in polyline.zipWithNext()) {
            distance += haversineMeters(pos1, pos2)
        }

        // Return the total distance.
        return distance.toFloat()
    }

    private fun haversineMeters(a: LatLng, b: LatLng): Double {
        val radius = 6_371_000.0
        val dLat = Math.toRadians(b.latitude - a.latitude)
        val dLon = Math.toRadians(b.longitude - a.longitude)
        val h = sin(dLat / 2).pow(2) + cos(Math.toRadians(a.latitude)) *
            cos(Math.toRadians(b.latitude)) * sin(dLon / 2).pow(2)
        return 2 * radius * asin(sqrt(h))
    }
}
