package com.nullpointer.runningcompose.domain.location

import android.location.Location
import com.nullpointer.runningcompose.data.local.datasource.location.LocationDataSource
import kotlinx.coroutines.flow.Flow

class LocationRepoImpl(
    locationDataSource: LocationDataSource
):LocationRepository {
    override val listLocations: Flow<List<Location>> =
        locationDataSource.listLocations
}