package com.nullpointer.runningcompose.data.location.local

import kotlin.math.*

data class LocationSample(val latitude: Double, val longitude: Double, val time: Long, val accuracy: Float)

/** Rejects stale, inaccurate and physically impossible GPS fixes. */
class LocationFilter(
    private val maxAccuracyMeters: Float = 35f,
    private val minDistanceMeters: Float = 3f,
    private val maxSpeedMetersPerSecond: Float = 12f,
) {
    private var lastAccepted: LocationSample? = null

    fun accept(location: LocationSample): Boolean {
        if (location.accuracy <= 0f || location.accuracy > maxAccuracyMeters) return false
        val previous = lastAccepted
        if (previous != null) {
            if (location.time <= previous.time) return false
            val distance = distanceMeters(previous, location)
            if (distance < minDistanceMeters) return false
            val elapsedSeconds = (location.time - previous.time) / 1_000f
            if (elapsedSeconds <= 0f || distance / elapsedSeconds > maxSpeedMetersPerSecond) return false
        }
        lastAccepted = location.copy()
        return true
    }

    fun reset() {
        lastAccepted = null
    }

    private fun distanceMeters(a: LocationSample, b: LocationSample): Float {
        val earthRadius = 6_371_000.0
        val dLat = Math.toRadians(b.latitude - a.latitude)
        val dLon = Math.toRadians(b.longitude - a.longitude)
        val h = sin(dLat / 2).pow(2) + cos(Math.toRadians(a.latitude)) *
            cos(Math.toRadians(b.latitude)) * sin(dLon / 2).pow(2)
        return (2 * earthRadius * asin(sqrt(h))).toFloat()
    }
}
