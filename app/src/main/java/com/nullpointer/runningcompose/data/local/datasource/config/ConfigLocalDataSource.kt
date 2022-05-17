package com.nullpointer.runningcompose.data.local.datasource.config

import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

interface ConfigLocalDataSource {
    val userConfig: Flow<UserConfig?>
    val mapConfig: Flow<MapConfig>
    val sortConfig: Flow<SortConfig>

    suspend fun changeUserConfig(nameUser: String?, weight: Float?)
    suspend fun changeMapConfig(style: MapStyle?, weight: Int?)
    suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?)
}