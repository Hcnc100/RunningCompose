package com.nullpointer.runningcompose.core.utils

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertEquals
import org.junit.Test

class PolylineSimplifierTest {
    @Test fun preservesEndpointsAndRemovesCollinearPoints() {
        val points = listOf(LatLng(19.0, -99.0), LatLng(19.00001, -99.00001), LatLng(19.00002, -99.00002))
        val result = PolylineSimplifier.simplify(points, toleranceMeters = 2.0)
        assertEquals(listOf(points.first(), points.last()), result)
    }

    @Test fun keepsSignificantTurns() {
        val points = listOf(LatLng(19.0, -99.0), LatLng(19.0, -98.999), LatLng(19.001, -98.999))
        assertEquals(points, PolylineSimplifier.simplify(points, toleranceMeters = 2.0))
    }
}
