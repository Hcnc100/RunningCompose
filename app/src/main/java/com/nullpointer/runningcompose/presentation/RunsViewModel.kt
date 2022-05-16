package com.nullpointer.runningcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.domain.config.ConfigRepoImpl
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.runs.RunRepoImpl
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.Run
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RunsViewModel @Inject constructor(
    private val runsRepository: RunRepository,
    confRepository: ConfigRepoImpl,
) : ViewModel() {

    val listRuns: Flow<List<Run>?> = confRepository.sortConfig.flatMapLatest {
        runsRepository.getListForTypeSort(it.sortType)
    }.catch {
        Timber.e("Error when load run $it")
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