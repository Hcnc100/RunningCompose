package com.nullpointer.runningcompose.ui.screens.config.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.models.data.AuthData
import com.nullpointer.runningcompose.models.data.PermissionsData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepo: ConfigRepository,
    private val authRepo: AuthRepository
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

    val authData = authRepo.authData.transform<AuthData?, Resource<AuthData?>> {
        emit(Resource.Success(it))
    }.catch {
        Timber.e("Error to load auth data")
        emit(Resource.Failure)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
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

    val sortConfig = configRepo.sortConfig.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load metrics config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SortConfig()
    )
    val numberRunsGraph = configRepo.numberRunsGraph.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load metrics config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        10
    )


    val permissionDataState = configRepo.isFirstPermissionLocation.combine(
        configRepo.isFirstPermissionNotify
    ) { location, notify ->
        PermissionsData(
            isFirstRequestLocation = location,
            isFirstRequestNotification = notify
        )
    }.map {
        Resource.Success(it)
    }.catch {
        Timber.e("Error in location permission state")
        Resource.Failure
    }
        .flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )





    fun changeFirstRequestLocationPermission() = launchSafeIO {
        configRepo.changeIsFirstPermissionLocation()
    }
    fun changeFirstRequestNotifyPermission() = launchSafeIO {
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

    fun changeNumberRunsGraph(numberRunsGraph: Int) = launchSafeIO {
        configRepo.changeNumberRunsGraph(numberRunsGraph)
    }

}