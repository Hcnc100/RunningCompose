package com.nullpointer.runningcompose.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.nullpointer.runningcompose.core.utils.Utility
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.auth.AuthRepository
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.TrackingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RunsViewModel @Inject constructor(
    private val runsRepository: RunRepository,
    private val configRepository: ConfigRepository,
    private val authRepository: AuthRepository,
    locationRepository: TrackingRepository
) : ViewModel() {

    init {
        Timber.e("Init time running")
    }

    private val _messageRuns = Channel<Int>()
    val messageRuns = _messageRuns.receiveAsFlow()




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

    val listRunsOrdered =
        combine(runsRepository.countRuns, configRepository.sortConfig) { _, config ->
            config
        }.flatMapLatest { config ->
            Pager(
                PagingConfig(
                    pageSize = 15,
                    initialLoadSize = 8
                )
            ) {
                runsRepository.getAllListRunOrdered(config.sortType, config.isReverse)
            }.flow.map {pagingData->
                pagingData.map {
                    RunData.fromRunEntity(it)
                }
            }
        }.cachedIn(viewModelScope).flowOn(Dispatchers.IO)


    fun insertNewRun(
        timeRun: Long,
        listPoints: List<List<LatLng>>,
        bitmap: Bitmap?
    ) = launchSafeIO {
        // * need weight user for calculate calories burned

        // ? Se estima que el costo energético de cada kilómetro que corres,
        // ? es de 1 kcal (1000 calorías) por cada kilogramo de peso corporal del corredo

        val weightUser = authRepository.authData.first()!!.weight
        val mapConfig = configRepository.mapConfig.first()
        val currentTime = System.currentTimeMillis()

        var distanceInMeters = 0f
        val listPolylineEncode = listPoints.asSequence().filter {
            it.size >= 2
        }.onEach {
            distanceInMeters += Utility.calculatePolylineLength(it)
        }.map {
            PolyUtil.encode(it)
        }.toList()
        val avgSpeedInMS = distanceInMeters / (timeRun / 1000f)
        val caloriesBurned = distanceInMeters * (weightUser / 1000f)

        val runData = RunData(
            avgSpeedInMeters = avgSpeedInMS,
            distanceInMeters = distanceInMeters,
            timeRunInMillis = timeRun,
            caloriesBurned = caloriesBurned,
            listPolyLineEncode = listPolylineEncode,
            mapConfig = mapConfig,
            timestamp = currentTime,
        )
        runsRepository.insertNewRun(runData, bitmap)
    }

    fun deleterRun(runData: RunData) = launchSafeIO {
        runsRepository.deleterRun(runData)
    }

    fun deleterListRun(listIds: List<Long>) = launchSafeIO {
        runsRepository.deleterListRuns(listIds)
    }
}
