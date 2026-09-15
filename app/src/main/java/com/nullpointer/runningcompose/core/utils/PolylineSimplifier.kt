package com.nullpointer.runningcompose.core.utils

import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

/** Keeps the shape of a route while reducing the number of points drawn by the map. */
object PolylineSimplifier {
    fun simplify(points: List<LatLng>, toleranceMeters: Double = 2.0): List<LatLng> {
        if (points.size < 3) return points
        val keep = BooleanArray(points.size)
        keep[0] = true
        keep[points.lastIndex] = true
        simplifyRange(points, 0, points.lastIndex, toleranceMeters, keep)
        return points.filterIndexed { index, _ -> keep[index] }
    }

    private fun simplifyRange(points: List<LatLng>, first: Int, last: Int, tolerance: Double, keep: BooleanArray) {
        var maxDistance = tolerance
        var index = -1
        for (i in first + 1 until last) {
            val distance = perpendicularDistance(points[i], points[first], points[last])
            if (distance > maxDistance) {
                maxDistance = distance
                index = i
            }
        }
        if (index >= 0) {
            keep[index] = true
            simplifyRange(points, first, index, tolerance, keep)
            simplifyRange(points, index, last, tolerance, keep)
        }
    }

    private fun perpendicularDistance(point: LatLng, start: LatLng, end: LatLng): Double {
        val latitudeScale = 111_320.0
        val x = (point.longitude - start.longitude) * latitudeScale * cos(Math.toRadians(start.latitude))
        val y = (point.latitude - start.latitude) * latitudeScale
        val endX = (end.longitude - start.longitude) * latitudeScale * cos(Math.toRadians(start.latitude))
        val endY = (end.latitude - start.latitude) * latitudeScale
        val denominator = hypot(endX, endY)
        if (denominator == 0.0) return hypot(x, y)
        return abs(endX * y - endY * x) / denominator
    }
}
