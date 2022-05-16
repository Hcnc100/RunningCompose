package com.nullpointer.runningcompose.domain.config

import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {
    val userConfig: Flow<UserConfig?>
    val mapConfig: Flow<MapConfig>
    val sortConfig: Flow<SortConfig>

    suspend fun changeUserConfig(nameUser: String? = null, weight: Float? = null)
    suspend fun changeMapConfig(style: MapStyle? = null, weight: Float? = null)
    suspend fun changeSortConfig(sortType: SortType? = null, isReverse: Boolean? = null)
}