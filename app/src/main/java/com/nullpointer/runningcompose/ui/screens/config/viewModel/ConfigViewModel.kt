package com.nullpointer.runningcompose.ui.screens.config.viewModel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.models.data.AuthData
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepository: ConfigRepository,
    authRepository: AuthRepository
) : ViewModel() {


    val authData = authRepository.authData.map<AuthData?, Resource<AuthData?>> {
        Resource.Success(it)
    }.catch {
        Timber.e("Error to load auth data")
        emit(Resource.Failure)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )

    val mapConfig = configRepository.settingsData.map {
        it.mapConfig
    }.flowOn(
        Dispatchers.IO
    ).catch {
        Timber.e("Error to load map config")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MapConfig()
    )


    val metrics = configRepository.settingsData.map {
        it.metricConfig
    }
        .flowOn(
            Dispatchers.IO
        ).catch {
            Timber.e("Error to load metrics config")
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MetricType.Meters
        )


    val numberRunsGraph = configRepository.settingsData
        .map { it.numberRunsGraph }
        .flowOn(
            Dispatchers.IO
        ).catch {
            Timber.e("Error to load metrics config")
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            10
        )


    fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        color: Color? = null,
    ) = launchSafeIO {
        configRepository.changeMapConfig(style, weight, color)
    }


    fun changeMetrics(
        metrics: MetricType,
    ) = launchSafeIO {
        configRepository.changeMetricConfig(metrics)
    }


    fun changeNumberRunsGraph(numberRunsGraph: Int) = launchSafeIO {
        configRepository.changeNumberRunsGraph(numberRunsGraph)
    }

}