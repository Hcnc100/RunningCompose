package com.nullpointer.runningcompose.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val listLocations: Flow<List<Location>>
}