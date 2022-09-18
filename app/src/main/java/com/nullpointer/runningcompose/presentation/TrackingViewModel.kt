package com.nullpointer.runningcompose.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.core.delegates.SavableComposeState
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.ui.screens.tracking.DrawPolyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    configRepository: ConfigRepository,
    locationRepository: TrackingRepository
) : ViewModel() {

    companion object {
        private const val KEY_DIALOG_CANCEL = "KEY_DIALOG_CANCEL"
        private const val KEY_DIALOG_SAVE = "KEY_DIALOG_SAVE"
    }

    val drawLinesData = combine(
        locationRepository.lastLocationSaved,
        configRepository.mapConfig
    ) { list, style ->
        DrawPolyData(list, style)
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        DrawPolyData()
    )

    val lastLocation = locationRepository.lastLocation.filter { isEnableAnimation }.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    val timeRun = locationRepository.timeTracking.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 0L
    )

    val stateTracking = locationRepository.stateTracking.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TrackingState.WAITING
    )
    var isShowDialogCancel by SavableComposeState(savedStateHandle, KEY_DIALOG_CANCEL, false)
        private set

    var isShowDialogSave by SavableComposeState(savedStateHandle, KEY_DIALOG_SAVE, false)
        private set

    private var isEnableAnimation by mutableStateOf(true)

    fun changeAnimation(isEnable: Boolean) {
        isEnableAnimation = isEnable
    }

    fun changeDialogCancel(isShowDialog:Boolean){
        isShowDialogCancel = isShowDialog
    }

    fun changeDialogSaved(isShowDialog:Boolean){
        isShowDialogSave = isShowDialog
    }
}