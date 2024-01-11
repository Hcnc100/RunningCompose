package com.nullpointer.runningcompose.core.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

object Utility {

    /**
     * This function calculates the total length of a polyline.
     *
     * @param polyline A list of LatLng objects representing the polyline.
     * @return The total length of the polyline in meters.
     */
    fun calculatePolylineLength(polyline: List<LatLng>): Float {
        // Initialize a variable to store the total distance.
        var distance = 0F

        // Iterate over each pair of consecutive points in the polyline.
        for ((pos1, pos2) in polyline.zipWithNext()) {
            // Create a FloatArray to store the result of the distance calculation.
            val result = FloatArray(1)

            // Calculate the distance between the two points and store the result in the FloatArray.
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )

            // Add the calculated distance to the total distance.
            distance += result[0]
        }

        // Return the total distance.
        return distance
    }
}