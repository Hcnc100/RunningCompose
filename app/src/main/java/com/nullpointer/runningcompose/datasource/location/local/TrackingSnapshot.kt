package com.nullpointer.runningcompose.datasource.location.local

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TrackingSnapshotPoint(val latitude: Double, val longitude: Double)

@Serializable
data class TrackingSnapshot(
    val points: List<List<TrackingSnapshotPoint>>,
    val state: String,
    val time: Long,
)

object TrackingSnapshotCodec {
    fun encode(snapshot: TrackingSnapshot): String = Json.encodeToString(snapshot)
    fun decode(value: String): TrackingSnapshot? = runCatching {
        Json.decodeFromString<TrackingSnapshot>(value)
    }.getOrNull()

    fun fromLatLng(points: List<List<LatLng>>, state: String, time: Long) = TrackingSnapshot(
        points.map { route -> route.map { TrackingSnapshotPoint(it.latitude, it.longitude) } }, state, time
    )
}
