package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.config.MapConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class RunsViewModel @Inject constructor(
    private val runsRepository: RunRepository,
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

    fun insertNewRun(timeRun: Long, listPoints: List<List<LatLng>>, configMap: MapConfig) =
        viewModelScope.launch(Dispatchers.IO) {
            val listPointsEncode = listPoints.map { PolyUtil.encode(it) }
            val run = Run(
                avgSpeed = 0F,
                distance = 0F,
                timeRunInMillis = timeRun,
                0F,
                listPolyLineEncode = listPointsEncode,
                configMap = configMap
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