package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.data.StatisticsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class StatisticsViewModel @Inject constructor(
    runsRepository: RunRepository,
) : ViewModel() {


    private val _messageStatistics = Channel<Int>()
    val messageStatistics = _messageStatistics.receiveAsFlow()

    val fullStatistics = combine(
        runsRepository.listRunsOrderByDate,
        runsRepository.totalStatisticRuns
    ) { list, statistics ->
        Resource.Success(
            StatisticsData(
                lastRunEntities = list,
                totalStatisticRuns = statistics
            )
        )

    }.catch<Resource<StatisticsData>> {
        Timber.e("Error when load run $it")
        _messageStatistics.trySend(R.string.error_load_runs_by_date)
        emit(Resource.Failure)
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        Resource.Loading
    )
}