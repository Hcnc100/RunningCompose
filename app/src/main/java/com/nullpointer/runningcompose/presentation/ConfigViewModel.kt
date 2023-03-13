package com.nullpointer.runningcompose.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
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


    val isFirstLocationPermission = configRepo.isFirstPermissionLocation.catch {
        Timber.e("Error in location permission state")
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        true
    )

    fun changeFirstRequestPermission() = launchSafeIO {
        configRepo.changeIsFirstPermissionLocation()
    }

    fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        color: Color? = null,
    ) = launchSafeIO {
        configRepo.changeMapConfig(style, weight, color)
    }


    fun changeMetrics(
        metrics: MetricType,
    ) = launchSafeIO {
        configRepo.changeMetricConfig(metrics)
    }

    fun changeSortConfig(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) = launchSafeIO {
        configRepo.changeSortConfig(sortType, isReverse)
    }

}