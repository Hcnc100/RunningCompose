package com.nullpointer.runningcompose.data.local.datasource.location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.data.local.location.SharedLocationManager
import kotlinx.coroutines.flow.Flow

class LocationDataSourceImpl(
    sharedLocationManager: SharedLocationManager,
) : LocationDataSource {
    override val listLocations: Flow<List<LatLng>> = sharedLocationManager.listLocationFlow()
    override val lastLocation: Flow<LatLng> = sharedLocationManager.lastLocationFlow()
}