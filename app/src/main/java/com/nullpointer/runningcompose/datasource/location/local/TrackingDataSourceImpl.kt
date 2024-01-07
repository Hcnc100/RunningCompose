package com.nullpointer.runningcompose.datasource.location.local

import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.data.location.local.SharedLocationManager
import com.nullpointer.runningcompose.models.types.TrackingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TrackingDataSourceImpl(
    sharedLocationManager: SharedLocationManager,
) : TrackingDataSource {

    private val _listLocationsSaved = MutableStateFlow<List<List<LatLng>>>(listOf(emptyList()))
    private val _stateTracking = MutableStateFlow(TrackingState.WAITING)
    private val _timeTracking = MutableStateFlow(0L)

    override val lastLocation: Flow<LatLng> = sharedLocationManager.lastLocationFlow()
    override val lastLocationSaved: Flow<List<List<LatLng>>> = _listLocationsSaved
    override val stateTracking: Flow<TrackingState> = _stateTracking
    override val timeTracking: Flow<Long> = _timeTracking


    override fun addNewLocation(newLocation: LatLng) {
        val oldList = _listLocationsSaved.value.toMutableList()
        val indexLast = oldList.lastIndex
        oldList[indexLast] = oldList[indexLast] + newLocation
        _listLocationsSaved.value = oldList
    }

    override fun changeStateTracking(newState: TrackingState) {
        _stateTracking.value = newState
    }

    override fun changeTimeTracking(newTime: Long) {
        _timeTracking.value = newTime
    }

    override fun addEmptyList() {
        val oldList = _listLocationsSaved.value.toMutableList() + listOf(emptyList())
        _listLocationsSaved.value = oldList
    }

    override fun clearValues() {
        _listLocationsSaved.value = listOf(emptyList())
        _timeTracking.value = 0L
    }


}