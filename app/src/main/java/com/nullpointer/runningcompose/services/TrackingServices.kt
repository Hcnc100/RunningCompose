package com.nullpointer.runningcompose.services

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.models.types.TrackingState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class TrackingServices : LifecycleService() {
    companion object {
        // * commands
        const val START_OR_RESUME_COMMAND = "START_COMMAND"
        const val STOP_COMMAND = "STOP_COMMAND"
        const val PAUSE_COMMAND = "PAUSE_COMMAND"

        // * delay timer
        const val TIMER_DELAY_TIMER = 50L

        private fun sendCommand(context: Context, command: String) {
            Intent(context, TrackingServices::class.java).apply {
                action = command
            }.let {
                context.startService(it)
            }
        }

        fun startServicesOrResume(context: Context) = sendCommand(context, START_OR_RESUME_COMMAND)
        fun finishServices(context: Context) = sendCommand(context, STOP_COMMAND)
        fun pauseServices(context: Context) = sendCommand(context, PAUSE_COMMAND)
    }

    @Inject
    lateinit var locationRepository: TrackingRepository
    private val timerRun = Timer()

    // * this no init immediately for error
    // * waiting init this services
    private val notificationServices by lazy {
        NotificationHelper(this)
    }

    override fun onCreate() {
        super.onCreate()
        locationRepository
            .lastLocation.combine(locationRepository.stateTracking) { location, state ->
                Pair(location, state)
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            // ! only send location when the tracking is running
            .filter {
                val (_, state) = it
                state == TRACKING
            }
            // ! this var notify new location
            // ? no send the listPoints
            .onEach {
                val (location, _) = it
                locationRepository.addNewLocation(location)
            }
            // ! when finish the services, reset static values
            .onCompletion {
                timerRun.resetValues()
                locationRepository.clearValues()
            }.launchIn(lifecycleScope)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            lifecycleScope.launch {
                val stateServices=locationRepository.stateTracking.first()
                when (it) {
                    START_OR_RESUME_COMMAND -> {
                        if(stateServices==WAITING){
                            notificationServices.startRunServices()
                        }
                        notificationServices.updateIsTracking(true)
                        timerRun.startTimer()
                        locationRepository.changeStateTracking(TRACKING)
                    }
                    PAUSE_COMMAND -> {
                        timerRun.stopTimer()
                        notificationServices.updateIsTracking(false)
                        // ! when pause tracking so add new empty list
                        locationRepository.addEmptyList()
                        locationRepository.changeStateTracking(PAUSE)
                    }
                    STOP_COMMAND -> {
                        timerRun.stopTimer()
                        // * reset state services to waiting
                        locationRepository.changeStateTracking(WAITING)
                        stopForeground(true)
                        stopSelf()
                    }
                    else -> Timber.e("Error action $it")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }



    private inner class Timer {

        //save the current time in seconds, but as long
        private var lastSecondTimestamp = 0L

        //saved the last time for the timer
        private var lastTimestamp = 0L

        //save all time for the timer
        private var timeRun = 0L

        private var timeRunInSeconds = 0L

        private var isEnable = false

        fun stopTimer(){
            isEnable=false
        }

        fun startTimer() {
            val timeStart = System.currentTimeMillis()
            isEnable=true
            lifecycleScope.launch(Dispatchers.Main) {
                //while is tracking
                while (isEnable) {
                    //save the last time stamp that is the current time minus the time start
                    lastTimestamp = System.currentTimeMillis() - timeStart
                    //update the time in millis
                    val newTime=timeRun + lastTimestamp
                    locationRepository.changeTimeTracking(newTime)
                    //if the time in millis is greater than the last time in seconds +1000
                    //so will add one to the current time in seconds
                    if (newTime >= lastSecondTimestamp + 1000L) {
                        //update the time in seconds
                        timeRunInSeconds += 1
                        notificationServices.updateTimeRun(timeRunInSeconds)
                        //update the current time
                        lastSecondTimestamp += 1000L
                    }
                    //sleep the process
                    delay(TIMER_DELAY_TIMER)
                }
            }
            //update the all time run
            timeRun += lastTimestamp
        }

        fun resetValues() {
            lastSecondTimestamp = 0L
            lastTimestamp = 0L
            timeRun = 0L
            timeRunInSeconds = 0L
            isEnable=false
        }
    }
}