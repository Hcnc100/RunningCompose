package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.domain.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    locationRepository: LocationRepository,
) : ViewModel() {
    val lastLocation = locationRepository.lastLocation
}