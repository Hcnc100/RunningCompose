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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

@AndroidEntryPoint
class TrackingServices : LifecycleService() {
    companion object {
        private const val START_COMMAND = "START_COMMAND"
        private const val STOP_COMMAND = "STOP_COMMAND"
        private val listPoints = MutableStateFlow<List<LatLng>>(emptyList())
        val showListPont = listPoints.asStateFlow()
        var isServicesLive by mutableStateOf(false)
        private set

        fun initServices(context: Context) {
            Intent(context, TrackingServices::class.java).apply {
                action = START_COMMAND
            }.let {
                context.startService(it)
            }
        }

        fun finishServices(context: Context) {
            Intent(context, TrackingServices::class.java).apply {
                action = STOP_COMMAND
            }.let {
                context.startService(it)
            }
        }
    }

    @Inject
    lateinit var locationRepository: LocationRepository

    override fun onCreate() {
        super.onCreate()
        locationRepository
            .listLocations
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach {
                Timber.d("LOcation send to tackin services")
                listPoints.value = ArrayList(it)
            }.onCompletion {
                Timber.d("LOaction cancelled")
            }.launchIn(lifecycleScope)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                START_COMMAND -> {
                    Timber.d("Services init")
                    isServicesLive = true
                }
                STOP_COMMAND -> {
                    Timber.d("Finish services")
                    isServicesLive = false
                    stopSelf()
                }
                else -> Timber.e("Error action $it")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

}