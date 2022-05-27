package com.nullpointer.runningcompose.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.domain.config.ConfigRepository
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
        null
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
        null
    )

    val isAuth= flow {
        configRepo.userConfig.collect{
            emit(it!=null)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )


    fun changeUserConfig(
        name: String? = null,
        weight: Float? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeUserConfig(name, weight)
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