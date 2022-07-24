package com.nullpointer.runningcompose.domain.location

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.data.local.datasource.location.TrackingDataSource
import com.nullpointer.runningcompose.models.types.TrackingState
import kotlinx.coroutines.flow.Flow

class TrackingRepoImpl(
    private val locationDataSource: TrackingDataSource,
) : TrackingRepository {
    override val lastLocation: Flow<LatLng> = locationDataSource.lastLocation
    override val lastLocationSaved: Flow<List<List<LatLng>>> = locationDataSource.lastLocationSaved
    override val stateTracking: Flow<TrackingState> = locationDataSource.stateTracking
    override val timeTracking: Flow<Long> = locationDataSource.timeTracking


    override fun addNewLocation(newLocation: LatLng) =
        locationDataSource.addNewLocation(newLocation)

    override fun changeStateTracking(newState: TrackingState) =
        locationDataSource.changeStateTracking(newState)

    override fun changeTimeTracking(newTime: Long) =
        locationDataSource.changeTimeTracking(newTime)

    override fun addEmptyList() = locationDataSource.addEmptyList()

    override fun clearValues() = locationDataSource.clearValues()


}