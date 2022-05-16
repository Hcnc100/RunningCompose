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

    suspend fun changeUserConfig(nameUser: String?, weight: Float?)
    suspend fun changeMapConfig(style: MapStyle?, weight: Float?)
    suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?)
}