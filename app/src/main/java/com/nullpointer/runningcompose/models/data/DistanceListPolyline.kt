package com.nullpointer.runningcompose.models.data

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.nullpointer.runningcompose.core.utils.Utility

data class DistanceListPolyline(
    val distanceInMeters: Float,
    val listEncodePolyline: List<String>
) {
    companion object {
        fun fromListPolyline(
            listPoints: List<List<LatLng>>
        ): DistanceListPolyline {
            var distanceInMeters = 0f
            val listPolylineEncode = listPoints.filter {
                it.size >= 2
            }.map {
                distanceInMeters += Utility.calculatePolylineLength(it)
                PolyUtil.encode(it)
            }

            return DistanceListPolyline(
                distanceInMeters = distanceInMeters,
                listEncodePolyline = listPolylineEncode
            )
        }
    }
}
