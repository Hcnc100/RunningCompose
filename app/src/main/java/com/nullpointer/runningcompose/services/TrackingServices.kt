package com.nullpointer.runningcompose.services

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.domain.location.LocationRepository
import com.nullpointer.runningcompose.models.types.TrackingState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrackingServices : LifecycleService() {
    companion object {
        private const val START_COMMAND = "START_COMMAND"
        private const val STOP_COMMAND = "STOP_COMMAND"
        private const val PAUSE_OR_RESUME_COMMAND = "PAUSE_OR_RESUME_COMMAND"
        private val listPoints = MutableStateFlow<List<LatLng>>(emptyList())
        val showListPont = listPoints.asStateFlow()
        var stateServices by mutableStateOf(WAITING)
            private set

        private fun sendCommand(context: Context, command: String) {
            Intent(context, TrackingServices::class.java).apply {
                action = command
            }.let {
                context.startService(it)
            }
        }

        fun startServices(context: Context) = sendCommand(context, START_COMMAND)
        fun finishServices(context: Context) = sendCommand(context, STOP_COMMAND)
        fun pauseOrResumeServices(context: Context) = sendCommand(context, PAUSE_OR_RESUME_COMMAND)
    }

    @Inject
    lateinit var locationRepository: LocationRepository

    override fun onCreate() {
        super.onCreate()
        locationRepository
            .lastLocation.onStart { listPoints.value = emptyList() }
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .filter { stateServices == TRACKING }
            .onEach {
                Timber.d("LOcation send to tackin services")
                listPoints.value = listPoints.value + it
            }
            .onCompletion { Timber.d("LOaction cancelled") }
            .launchIn(lifecycleScope)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                START_COMMAND -> {
                    Timber.d("Services init")
                    stateServices = TRACKING
                }
                PAUSE_OR_RESUME_COMMAND -> {
                    stateServices = if (stateServices == TRACKING) PAUSE else TRACKING
                }
                STOP_COMMAND -> {
                    Timber.d("Finish services")
                    stateServices = WAITING
                    stopSelf()
                }
                else -> Timber.e("Error action $it")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}