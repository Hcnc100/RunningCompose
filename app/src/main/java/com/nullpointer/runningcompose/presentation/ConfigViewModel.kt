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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
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

    fun changeUserConfig(
        name: String? = null,
        weight: Float? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeUserConfig(name, weight)
    }

    fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        metricType: MetricType? = null,
        color: Color? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeMapConfig(style, weight, metricType,color)
    }

    fun changeSortConfig(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) = viewModelScope.launch(Dispatchers.IO) {
        configRepo.changeSortConfig(sortType, isReverse)
    }

}