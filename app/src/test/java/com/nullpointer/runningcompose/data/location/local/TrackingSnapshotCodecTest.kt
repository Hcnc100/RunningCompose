package com.nullpointer.runningcompose.datasource.location.local

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TrackingSnapshotCodecTest {
    @Test fun roundTripPreservesSession() {
        val snapshot = TrackingSnapshotCodec.fromLatLng(
            listOf(listOf(LatLng(19.0, -99.0)), emptyList()), "PAUSE", 12_500L
        )
        assertEquals(snapshot, TrackingSnapshotCodec.decode(TrackingSnapshotCodec.encode(snapshot)))
    }

    @Test fun invalidSnapshotIsIgnored() {
        assertNull(TrackingSnapshotCodec.decode("not-json"))
    }

    @Test fun snapshotCanContainPausedSegments() {
        val snapshot = TrackingSnapshotCodec.fromLatLng(
            listOf(listOf(LatLng(19.0, -99.0)), emptyList()), "PAUSE", 99L
        )
        val restored = TrackingSnapshotCodec.decode(TrackingSnapshotCodec.encode(snapshot))
        assertEquals(2, restored?.points?.size)
        assertEquals("PAUSE", restored?.state)
        assertEquals(99L, restored?.time)
    }
}
