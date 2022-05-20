package com.nullpointer.runningcompose.data.local.datasource.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationDataSource {
    val listLocations:Flow<List<Location>>
}