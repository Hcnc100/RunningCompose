package com.nullpointer.runningcompose.data.location.local

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LocationFilterTest {
    private fun location(time: Long, latitude: Double = 19.0, accuracy: Float = 5f) =
        LocationSample(latitude, -99.0, time, accuracy)

    @Test fun rejectsInaccurateFixes() {
        assertFalse(LocationFilter().accept(location(1_000, accuracy = 50f)))
    }

    @Test fun rejectsDuplicateAndStaleFixes() {
        val filter = LocationFilter()
        assertTrue(filter.accept(location(1_000)))
        assertFalse(filter.accept(location(1_000)))
        assertFalse(filter.accept(location(2_000, latitude = 19.000001)))
    }

    @Test fun rejectsImpossibleSpeed() {
        val filter = LocationFilter()
        assertTrue(filter.accept(location(1_000)))
        assertFalse(filter.accept(location(2_000, latitude = 19.001)))
    }
}
