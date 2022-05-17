package com.nullpointer.runningcompose.data.local.datasource.config

import com.nullpointer.runningcompose.data.local.config.ConfigUserStore
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow

class ConfigLocalDataSourceImpl(
    private val configUserStore: ConfigUserStore
):ConfigLocalDataSource {
    override val userConfig: Flow<UserConfig?> = configUserStore.getUserConfig()
    override val mapConfig: Flow<MapConfig> = configUserStore.getMapConfig()
    override val sortConfig: Flow<SortConfig> = configUserStore.getSortConfig()

    override suspend fun changeUserConfig(nameUser: String?, weight: Float?){
        configUserStore.changeUserConf(nameUser,weight)
    }

    override suspend fun changeMapConfig(style: MapStyle?, weight: Int?) {
        configUserStore.changeMapConfig(style,weight)
    }

    override suspend fun changeSortConfig(sortType: SortType?, isReverse: Boolean?) {
        configUserStore.changeSortConf(sortType,isReverse)
    }


}