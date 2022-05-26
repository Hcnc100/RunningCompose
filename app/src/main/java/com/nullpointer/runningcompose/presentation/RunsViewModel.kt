package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.Utility
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.MapConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@HiltViewModel
class RunsViewModel @Inject constructor(
    private val runsRepository: RunRepository,
    private val configRepository: ConfigRepository,
) : ViewModel() {

    private val _messageRuns = Channel<Int>()
    val messageRuns = _messageRuns.receiveAsFlow()

    val statisticsRuns = runsRepository.totalStatisticRuns.catch {
        Timber.e("Error to load statatistics $it")
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val listRuns = runsRepository.listRuns.catch {
        Timber.e("Error when load run $it")
        _messageRuns.trySend(R.string.error_load_runs)
        emit(emptyList())
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        null
    )

    val isStatisticsLoad = combine(listRuns, statisticsRuns) { listRuns, statistics ->
        statistics == null || listRuns == null
    }

    fun insertNewRun(newRun: Run) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.insertNewRun(newRun)
    }

    fun insertNewRun(
        timeRun: Long,
        listPoints: List<List<LatLng>>,
    ) = viewModelScope.launch(Dispatchers.IO) {
        val weightUser = configRepository.userConfig.first()?.weight ?: 80F
        val mapConfig = configRepository.mapConfig.first()
        var distanceInMeters = 0f
        val listPolylineEncode = listPoints.map {
            distanceInMeters += Utility.calculatePolylineLength(it)
            PolyUtil.encode(it)
        }
        val avgSpeedInMS = distanceInMeters / (timeRun / 1000f)

        //Se estima que el costo energético de cada kilómetro que corres,
        // es de 1 kcal (1000 calorías) por cada kilogramo de peso corporal del corredo

        val caloriesBurned = distanceInMeters * (weightUser / 1000f)
        val run = Run(
            avgSpeed = avgSpeedInMS,
            distance = distanceInMeters,
            timeRunInMillis = timeRun,
            caloriesBurned = caloriesBurned,
            listPolyLineEncode = listPolylineEncode,
            configMap = mapConfig
        )
        runsRepository.insertNewRun(run)
    }

    fun deleterRun(run: Run) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.deleterRun(run)
    }

    fun deleterListRun(listIds: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.deleterListRuns(listIds)
    }
}