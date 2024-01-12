package com.nullpointer.runningcompose.services

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.nullpointer.runningcompose.domain.location.TrackingRepository
import com.nullpointer.runningcompose.models.types.TrackingState.PAUSE
import com.nullpointer.runningcompose.models.types.TrackingState.TRACKING
import com.nullpointer.runningcompose.models.types.TrackingState.WAITING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
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
    private val timerTrackingRunningRunTracking = TimerTrackingRunning()

    // * this no init immediately for error
    // * waiting init this services
    private val notificationServices by lazy {
        NotificationHelper(this)
    }

    /**
     * This method is called when the service is created.
     * It sets up a flow that combines the last known location and the tracking state.
     * The flow is tied to the lifecycle of the service and only emits data when the lifecycle state is at least STARTED.
     * The flow is filtered to only pass data when the tracking state is TRACKING.
     * For each pair of data that passes the filter, the new location is added to the repository.
     * When the flow completes (e.g., when the service stops), the timer values are reset and the location repository values are cleared.
     * The flow is launched in the lifecycle scope of the service, meaning it will automatically be collected when the service is destroyed, preventing memory leaks.
     */
    override fun onCreate() {
        super.onCreate()
        locationRepository
            .lastLocation.combine(locationRepository.stateTracking) { location, state ->
                // Combine the last known location and the tracking state into a Pair
                Pair(location, state)
            }
            .flowWithLifecycle(
                lifecycle,
                Lifecycle.State.STARTED
            ) // Bind the flow to the lifecycle of the service
            .filter {
                // Only pass data when the tracking state is TRACKING
                val (_, trackingState) = it
                trackingState == TRACKING
            }
            .onEach {
                // For each pair of data that passes the filter, add the new location to the repository
                val (location, _) = it
                locationRepository.addNewLocation(location)
            }
            .onCompletion {
                // When the flow completes, reset the timer values and clear the location repository values
                timerTrackingRunningRunTracking.resetValues()
                locationRepository.clearValues()
            }.launchIn(lifecycleScope) // Launch the flow in the lifecycle scope of the service
    }

    /**
     * This method is called when the service receives a command.
     * It checks the action of the intent and performs different actions depending on the action.
     *
     * @param intent The intent that started the service.
     * @param flags Additional data about this start request.
     * @param startId A unique integer representing this specific request to start.
     * @return The return value indicates what semantics the system should use for the service's current started state.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let { commandAction ->
            lifecycleScope.launch {
                val stateServices = locationRepository.stateTracking.first()
                when (commandAction) {
                    // If the action is START_OR_RESUME_COMMAND, the code checks if the service state is WAITING.
                    // If so, it starts the notification service, updates the tracking state to TRACKING, starts the timer,
                    // and changes the tracking state in the location repository to TRACKING.
                    START_OR_RESUME_COMMAND -> {
                        if (stateServices == WAITING) {
                            notificationServices.startRunServices()
                        }
                        notificationServices.updateIsTracking(true)
                        timerTrackingRunningRunTracking.startTimer()
                        locationRepository.changeStateTracking(TRACKING)
                    }
                    // If the action is PAUSE_COMMAND, the code stops the timer, updates the tracking state to false in the notification service,
                    // adds a new empty list to the location repository, and changes the tracking state in the location repository to PAUSE.
                    PAUSE_COMMAND -> {
                        timerTrackingRunningRunTracking.stopTimer()
                        notificationServices.updateIsTracking(false)
                        // ! when pause tracking so add new empty list
                        locationRepository.addEmptyList()
                        locationRepository.changeStateTracking(PAUSE)
                    }
                    // If the action is STOP_COMMAND, the code stops the timer, changes the tracking state in the location repository to WAITING,
                    // stops the foreground service, and stops the service.
                    STOP_COMMAND -> {
                        timerTrackingRunningRunTracking.stopTimer()
                        // * reset state services to waiting
                        locationRepository.changeStateTracking(WAITING)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            stopForeground(STOP_FOREGROUND_REMOVE)
                        } else {
                            stopForeground(true)
                        }
                        stopSelf()
                    }
                    // If the action is none of the above, the code logs an error.
                    else -> Timber.e("Error action $commandAction")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    /**
     * This is a helper class for managing a running timer within the TrackingServices class.
     * It keeps track of the total time, the last recorded time, and the current time in seconds.
     * It also provides methods to start, stop, and reset the timer.
     */
    private inner class TimerTrackingRunning {

        // The current time in seconds
        private var currentSecondTimestamp = 0L

        // The last recorded time for the timer
        private var lastRecordedTimestamp = 0L

        // The total time for the timer
        private var totalTime = 0L

        // The total time in seconds
        private var totalTimeInSeconds = 0L

        // Indicates if the timer is running
        private var isTimerRunning = false

        /**
         * Stops the timer by setting isTimerRunning to false.
         */
        fun stopTimer() {
            isTimerRunning = false
        }

        /**
         * Starts the timer. It records the start time, sets isTimerRunning to true,
         * and launches a coroutine that updates the last recorded time and total time
         * as long as the timer is running. It also updates the tracking time in the
         * location repository and the running time in the notification services every second.
         */
        fun startTimer() {
            // Record the current time in milliseconds as the start time
            val startTime = System.currentTimeMillis()

            // Set the timer as running
            isTimerRunning = true

            // Launch a coroutine on the main thread
            lifecycleScope.launch(Dispatchers.IO) {
                // While the timer is running
                while (isTimerRunning) {
                    // Update the last recorded time as the difference between the current time and the start time
                    lastRecordedTimestamp = System.currentTimeMillis() - startTime

                    // Calculate the new time as the sum of the total time and the last recorded time
                    val newTime = totalTime + lastRecordedTimestamp

                    // Update the tracking time in the location repository with the new time
                    locationRepository.changeTimeTracking(newTime)

                    // If the new time is greater or equal to the current time in seconds plus 1000 milliseconds
                    if (newTime >= currentSecondTimestamp + 1000L) {
                        // Increment the total time in seconds
                        totalTimeInSeconds += 1

                        // Update the running time in the notification services with the total time in seconds
                        notificationServices.updateTimeRun(totalTimeInSeconds)

                        // Increment the current time in seconds by 1000 milliseconds
                        currentSecondTimestamp += 1000L
                    }

                    // Delay the execution of the coroutine by 50 milliseconds
                    delay(TIMER_DELAY_TIMER)
                }
            }

            // Add the last recorded time to the total time
            totalTime += lastRecordedTimestamp
        }

        /**
         * Resets all the values to their initial state.
         */
        fun resetValues() {
            totalTime = 0L
            isTimerRunning = false
            lastRecordedTimestamp = 0L
            totalTimeInSeconds = 0L
            currentSecondTimestamp = 0L
        }
    }
}