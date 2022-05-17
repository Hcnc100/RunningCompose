package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.Run
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

    fun insertNewRun(newRun: Run) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.insertNewRun(newRun)
    }

    fun deleterRun(run: Run) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.deleterRun(run)
    }

    fun deleterListRun(listIds: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        runsRepository.deleterListRuns(listIds)
    }
}