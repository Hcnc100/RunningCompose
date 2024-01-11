package com.nullpointer.runningcompose.presentation

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.delegates.SavableComposeState
import com.nullpointer.runningcompose.core.utils.launchSafeIO
import com.nullpointer.runningcompose.domain.config.ConfigRepository
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.domain.runs.RunRepository
import com.nullpointer.runningcompose.models.data.DrawPolyData
import com.nullpointer.runningcompose.models.types.TrackingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    configRepository: ConfigRepository,
    private val locationRepository: TrackingRepository,
    private val runsRepository: RunRepository
) : ViewModel() {
    companion object {
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

    val lastLocation = locationRepository
        .lastLocation
        .filter { !isSavingRun }
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val timeRun = locationRepository
        .timeTracking
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0L
        )

    val stateTracking = locationRepository.stateTracking.flowOn(Dispatchers.IO).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TrackingState.WAITING
    )

    var isSavingRun by SavableComposeState(savedStateHandle, KEY_DIALOG_SAVE, false)
        private set

    private val _message = Channel<Int>()
    val message = _message.receiveAsFlow()

    fun insertNewRun(
        getMapBitmap: suspend () -> Bitmap?,
        onSuccess: () -> Unit
    ) = launchSafeIO(
        blockBefore = { isSavingRun = true },
        blockAfter = { isSavingRun = false },
        blockException = {
            Timber.e("Error for save run data $it")
            _message.trySend(R.string.error_saving_run)
        },
        blockIO = {
            val currentListPolyline = locationRepository.lastLocationSaved.first()
            val currentTimeRun = locationRepository.timeTracking.first()
            runsRepository.insertNewRun(
                bitmap = getMapBitmap(),
                listPoints = currentListPolyline,
                timeRunInMillis = currentTimeRun
            )
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    )
}