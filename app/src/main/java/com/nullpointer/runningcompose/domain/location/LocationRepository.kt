package com.nullpointer.runningcompose.domain.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val listLocations:Flow<List<LatLng>>
    val lastLocation:Flow<LatLng>
}