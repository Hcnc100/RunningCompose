package com.nullpointer.runningcompose.datasource.location.local

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nullpointer.runningcompose.data.location.local.SharedLocationManager
import com.nullpointer.runningcompose.models.types.TrackingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Implementation of the TrackingDataSource interface.
 *
 * @property sharedLocationManager The shared location manager used to get location updates.
 */
private val Context.trackingStore by preferencesDataStore("active_tracking")

class TrackingDataSourceImpl(
    private val sharedLocationManager: SharedLocationManager,
    private val context: Context,
    private val scope: CoroutineScope,
) : TrackingDataSource {

    // Mutable state flows for tracking data
    private val _listLocationsSaved = MutableStateFlow<List<List<LatLng>>>(listOf(emptyList()))
    private val _stateTracking = MutableStateFlow(TrackingState.WAITING)
    private val _timeTracking = MutableStateFlow(0L)
    private var lastTimePersisted = 0L

    init {
        scope.launch(Dispatchers.IO) {
            context.trackingStore.data.first()[TRACKING_KEY]?.let { encoded ->
                TrackingSnapshotCodec.decode(encoded)?.let { saved ->
                    _listLocationsSaved.value = saved.points.map { points -> points.map { LatLng(it.latitude, it.longitude) } }
                    _stateTracking.value = runCatching { TrackingState.valueOf(saved.state) }
                        .getOrDefault(TrackingState.WAITING)
                    _timeTracking.value = saved.time
                }
            }
        }
    }

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
        val current = _listLocationsSaved.value
        val updated = current.dropLast(1) + listOf(current.last() + newLocation)
        _listLocationsSaved.value = updated
        persistIfNeeded()
    }

    /**
     * Changes the current tracking state.
     *
     * @param newState The new tracking state.
     */
    override fun changeStateTracking(newState: TrackingState) {
        _stateTracking.value = newState
        persist()
    }

    /**
     * Changes the total tracking time.
     *
     * @param newTime The new tracking time.
     */
    override fun changeTimeTracking(newTime: Long) {
        _timeTracking.value = newTime
        if (newTime - lastTimePersisted >= 1_000L) persistIfNeeded()
    }

    /**
     * Adds an empty list to the saved locations list.
     */
    override fun addEmptyList() {
        val oldList = _listLocationsSaved.value.toMutableList() + listOf(emptyList())
        _listLocationsSaved.value = oldList
        persist()
    }

    /**
     * Clears the saved locations list and the tracking time.
     */
    override fun clearValues() {
        _listLocationsSaved.value = listOf(emptyList())
        _timeTracking.value = 0L
        scope.launch(Dispatchers.IO) { context.trackingStore.edit { it.remove(TRACKING_KEY) } }
    }

    private fun persistIfNeeded() {
        if (_listLocationsSaved.value.flatten().isNotEmpty()) persist()
    }

    private fun persist() {
        val snapshot = TrackingSnapshotCodec.fromLatLng(
            _listLocationsSaved.value, _stateTracking.value.name, _timeTracking.value
        )
        lastTimePersisted = snapshot.time
        scope.launch(Dispatchers.IO) {
            context.trackingStore.edit { it[TRACKING_KEY] = TrackingSnapshotCodec.encode(snapshot) }
        }
    }

    private companion object {
        val TRACKING_KEY = stringPreferencesKey("active_tracking_snapshot")
    }
}
