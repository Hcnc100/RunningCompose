package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.domain.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    locationRepository: LocationRepository,
) : ViewModel() {

    val listLocations:Flow<List<LatLng>> = locationRepository.listLocations

    val lastLocation= locationRepository.lastLocation
}