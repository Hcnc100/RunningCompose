package com.nullpointer.runningcompose.domain.config

import com.nullpointer.runningcompose.data.local.datasource.config.ConfigLocalDataSource
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class ConfigRepoImpl(
    private val configLocalDataSource: ConfigLocalDataSource,
) : ConfigRepository {
    override val userConfig: Flow<UserConfig?> = configLocalDataSource.userConfig
    override val mapConfig: Flow<MapConfig> = configLocalDataSource.mapConfig
    override val sortConfig: Flow<SortConfig> = configLocalDataSource.sortConfig

    override suspend fun changeUserConfig(nameUser: String?, weight: Float?) {
        configLocalDataSource.changeUserConfig(nameUser, weight)
    }

    override suspend fun changeMapConfig(style: MapStyle?, weight: Float?) {
        configLocalDataSource.changeMapConfig(style, weight)
    }

    override suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?) {
        configLocalDataSource.changeSortConfig(sortType, isReverse)
    }
}