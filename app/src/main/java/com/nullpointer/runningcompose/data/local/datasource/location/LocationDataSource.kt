package com.nullpointer.runningcompose.data.local.datasource.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    val lastLocation:Flow<LatLng>
}