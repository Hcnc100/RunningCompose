package com.nullpointer.runningcompose.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.LoginStatus
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepo: ConfigRepository,
) : ViewModel() {

    val mapConfig = configRepo.mapConfig.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load map config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MapConfig()
    )

    val userConfig = configRepo.userConfig.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load map config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val metrics = configRepo.metricsConfig.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load metrics config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MetricType.Meters
    )

    val sortConfig=configRepo.sortConfig.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load metrics config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SortConfig()
    )

    val stateAuth = flow {
        configRepo.userConfig.collect {
            val status = if (it != null) LoginStatus.Authenticated else LoginStatus.Unauthenticated
            emit(status)
        }
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        LoginStatus.Authenticating
    )

    val isFirstLocationPermission = configRepo.isFirstPermissionLocation.catch {
        Timber.e("Error in location permission state")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        true
    )

    fun changeFirstRequestPermission() = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeIsFirstPermissionLocation()
    }


    fun changeUserConfig(
        userConfig: UserConfig
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeUserConfig(userConfig)
    }

    fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        color: Color? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeMapConfig(style, weight, color)
    }


    fun changeMetrics(
        metrics: MetricType,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeMetricConfig(metrics)
    }

    fun changeSortConfig(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeSortConfig(sortType, isReverse)
    }

}