package com.nullpointer.runningcompose.datasource.location.local

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.models.types.TrackingState
import kotlinx.coroutines.flow.Flow

interface TrackingDataSource {
    val lastLocation: Flow<LatLng>
    val lastLocationSaved: Flow<List<List<LatLng>>>
    val stateTracking: Flow<TrackingState>
    val timeTracking: Flow<Long>

    fun addNewLocation(newLocation: LatLng)
    fun changeStateTracking(newState: TrackingState)
    fun changeTimeTracking(newTime: Long)
    fun addEmptyList()
    fun clearValues()
}