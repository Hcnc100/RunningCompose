package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.data.PermissionsData
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.TrackingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RunsViewModel @Inject constructor(
    private val runsRepository: RunRepository,
    private val configRepository: ConfigRepository,
    locationRepository: TrackingRepository
) : ViewModel() {

    private val _messageRuns = Channel<Int>()
    val messageRuns = _messageRuns.receiveAsFlow()

    val permissionDataState = configRepository.settingsData
        .map {
            Resource.Success(
                PermissionsData(
                    it.isFirstRequestLocationPermission,
                    it.isFirstRequestNotifyPermission
                )
            )
        }
        .catch {
            Timber.e("Error in location permission state")
            Resource.Failure
        }
        .flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            Resource.Loading
        )


    val sortConfig = configRepository.settingsData.map {
        it.sortConfig
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SortConfig()
    )

    val metricType = configRepository.settingsData.map {
        it.metricConfig
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        MetricType.Meters
    )

    val stateTracking = locationRepository
        .stateTracking
        .flowOn(Dispatchers.IO).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            TrackingState.WAITING
        )

    val numberRuns = runsRepository.countRuns
        .flowOn(Dispatchers.IO)
        .catch {
            Timber.e("Error to load number runs")
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            -1
        )

    val listRunsOrdered = configRepository.settingsData.map {
        it.sortConfig
    }.distinctUntilChanged()
        .flatMapLatest { config ->
            Pager(
                PagingConfig(
                    pageSize = 15,
                    initialLoadSize = 8,
                    enablePlaceholders = false
                )
            ) {
                runsRepository.getAllListRunOrdered(config.sortType, config.isReverse)
            }.flow.map { pagingData ->
                pagingData.map(RunData::fromRunEntity)
            }
        }.cachedIn(viewModelScope).flowOn(Dispatchers.IO)

    fun deleterListRun(listIds: List<Long>) = launchSafeIO {
        runsRepository.deleterListRuns(listIds)
    }

    fun changeFirstRequestLocationPermission() = launchSafeIO {
        configRepository.changeIsFirstPermissionLocation()
    }

    fun changeFirstRequestNotifyPermission() = launchSafeIO {
        configRepository.changeIsFirstPermissionLocation()
    }

    fun changeSortConfig(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) = launchSafeIO {
        configRepository.changeSortConfig(sortType, isReverse)
    }

}
