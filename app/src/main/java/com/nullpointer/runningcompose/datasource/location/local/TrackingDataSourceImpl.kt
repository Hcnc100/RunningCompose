package com.nullpointer.runningcompose.datasource.location.local

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.data.location.local.SharedLocationManager
import com.nullpointer.runningcompose.models.types.TrackingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implementation of the TrackingDataSource interface.
 *
 * @property sharedLocationManager The shared location manager used to get location updates.
 */
class TrackingDataSourceImpl(
    private val sharedLocationManager: SharedLocationManager,
) : TrackingDataSource {

    // Mutable state flows for tracking data
    private val _listLocationsSaved = MutableStateFlow<List<List<LatLng>>>(listOf(emptyList()))
    private val _stateTracking = MutableStateFlow(TrackingState.WAITING)
    private val _timeTracking = MutableStateFlow(0L)

    // Publicly exposed flows for tracking data
    override val lastLocation: Flow<LatLng> = sharedLocationManager.lastLocationFlow()
    override val lastLocationSaved: Flow<List<List<LatLng>>> = _listLocationsSaved
    override val stateTracking: Flow<TrackingState> = _stateTracking
    override val timeTracking: Flow<Long> = _timeTracking

    /**
     * Adds a new location to the last saved locations list.
     *
     * @param newLocation The new location to add.
     */
    override fun addNewLocation(newLocation: LatLng) {
        val oldList = _listLocationsSaved.value.toMutableList()
        val indexLast = oldList.lastIndex
        oldList[indexLast] = oldList[indexLast] + newLocation
        _listLocationsSaved.value = oldList
    }

    /**
     * Changes the current tracking state.
     *
     * @param newState The new tracking state.
     */
    override fun changeStateTracking(newState: TrackingState) {
        _stateTracking.value = newState
    }

    /**
     * Changes the total tracking time.
     *
     * @param newTime The new tracking time.
     */
    override fun changeTimeTracking(newTime: Long) {
        _timeTracking.value = newTime
    }

    /**
     * Adds an empty list to the saved locations list.
     */
    override fun addEmptyList() {
        val oldList = _listLocationsSaved.value.toMutableList() + listOf(emptyList())
        _listLocationsSaved.value = oldList
    }

    /**
     * Clears the saved locations list and the tracking time.
     */
    override fun clearValues() {
        _listLocationsSaved.value = listOf(emptyList())
        _timeTracking.value = 0L
    }
}