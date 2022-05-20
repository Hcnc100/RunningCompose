package com.nullpointer.runningcompose.data.local.datasource.location

import android.location.Location
import com.nullpointer.runningcompose.data.local.location.SharedLocationManager
import kotlinx.coroutines.flow.Flow

class LocationDataSourceImpl(
    sharedLocationManager: SharedLocationManager
):LocationDataSource {
    override val listLocations: Flow<List<Location>> = sharedLocationManager.locationFlow()
}