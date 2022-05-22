package com.nullpointer.runningcompose.domain.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.data.local.datasource.location.LocationDataSource
import kotlinx.coroutines.flow.Flow

class LocationRepoImpl(
    locationDataSource: LocationDataSource,
) : LocationRepository {
    override val lastLocation: Flow<LatLng> =
        locationDataSource.lastLocation
}