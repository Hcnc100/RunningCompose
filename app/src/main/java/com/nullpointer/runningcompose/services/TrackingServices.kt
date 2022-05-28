package com.nullpointer.runningcompose.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.clearActionsNotification
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.domain.location.LocationRepository
import com.nullpointer.runningcompose.models.types.TrackingState.*
import com.nullpointer.runningcompose.ui.activitys.MainActivity
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
        private const val START_COMMAND = "START_COMMAND"
        private const val STOP_COMMAND = "STOP_COMMAND"
        private const val PAUSE_OR_RESUME_COMMAND = "PAUSE_OR_RESUME_COMMAND"

        // * delay timer
        const val TIMER_DELAY_TIMER = 50L

        private const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        private const val NOTIFICATION_ID = 123456789


        // * var to save state servicews
        var stateServices by mutableStateOf(WAITING)
            private set

        private val listPoints: MutableList<MutableList<LatLng>> = mutableListOf(mutableListOf())
        val showListPoints: List<List<LatLng>> = listPoints

        private val counterPoints = MutableStateFlow(0)
        val showCounterPoint = counterPoints.asStateFlow()

        private val timeInMillis = MutableStateFlow(0L)
        val showTimeInMillis = timeInMillis.asStateFlow()


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

    private val timerRun = Timer()

    // * this no init immediately for error
    // * waiting init this services
    private val notificationServices by lazy {
        NotificationServices()
    }

    override fun onCreate() {
        super.onCreate()
        locationRepository
            .lastLocation
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            // ! only send location when the tracking is running
            .filter { stateServices == TRACKING }
            // ! this var notify new location
            // ? no send the listPoints
            .onEach {
                counterPoints.value = counterPoints.value + 1
                listPoints.last().add(it)
            }
            // ! when finish the services, reset static values
            .onCompletion {
                timerRun.resetValues()
                listPoints.clear()
                listPoints.add(mutableListOf())
                timeInMillis.value = 0
            }.launchIn(lifecycleScope)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                START_COMMAND -> {
                    stateServices = TRACKING
                    notificationServices.startRunServices()
                    timerRun.startTimer()
                }
                PAUSE_OR_RESUME_COMMAND -> {
                    stateServices = if (stateServices == TRACKING) {
                        notificationServices.updateAction(false)
                        // ! when pause tracking so add new empty list
                        // * the user can pause this services and this points may not be
                        // * consecutive
                        listPoints.add(mutableListOf())
                        PAUSE
                    } else {
                        notificationServices.updateAction(true)
                        // ! only start services when resume services
                        timerRun.startTimer()
                        TRACKING
                    }
                }
                STOP_COMMAND -> {
                    // * reset state services to waiting
                    stateServices = WAITING
                    stopForeground(true)
                    stopSelf()
                }
                else -> Timber.e("Error action $it")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private inner class NotificationServices {

        private val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        private val context get() = this@TrackingServices
        private val currentNotification by lazy { createBaseNotification() }
        private val pendingIntentAction by lazy { createPendingIntentAction() }

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannelNotification()
            }
        }

        private fun createChannelNotification() {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notifyManager.createNotificationChannel(channel)
        }

        private fun createPendingIntentAction(): PendingIntent? {
            val actionIntent = Intent(
                context,
                TrackingServices::class.java
            ).apply { action = PAUSE_OR_RESUME_COMMAND }

            return PendingIntent.getService(
                context,
                1,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        private fun createBaseNotification(): NotificationCompat.Builder {
            return NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(context.getString(R.string.app_name))
                .setContentText("00:00:00:00")
                .setContentIntent(createPendingIntentCompose())
        }

        private fun createPendingIntentCompose(): PendingIntent? {
            // * create deep link
            // * this go to post for notification
            val deepLinkIntent = Intent(Intent.ACTION_VIEW,
                "https://www.running-compose.com/tracking".toUri(),
                context,
                MainActivity::class.java)
            // * create pending intent compose
            val deepLinkPendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(deepLinkIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            return deepLinkPendingIntent
        }

        fun updateAction(isTracking: Boolean) {
            currentNotification.clearActionsNotification()
            val textAction = if (isTracking) R.string.text_action_pause else R.string.textActionResumen
            currentNotification.addAction(
                R.drawable.ic_pause,
                context.getString(textAction),
                pendingIntentAction
            )

            notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
        }

        fun startRunServices() {
            startForeground(NOTIFICATION_ID, currentNotification.build())
            updateAction(true)
        }

        fun updateTimeRunNotification(timeRun: Long) {
            // * update time from notification with the seconds
            currentNotification.setContentText(
                // ? the time run passed as parameter is in seconds so, convert to milliseconds
                (timeRun * 1000L).toFullFormatTime(false)
            )
            notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
        }

    }


    private inner class Timer {

        //save the current time in seconds, but as long
        private var lastSecondTimestamp = 0L

        //saved the last time for the timer
        private var lastTimestamp = 0L

        //save all time for the timer
        private var timeRun = 0L

        private var timeRunInSeconds = 0L

        fun startTimer() {
            val timeStart = System.currentTimeMillis()
            lifecycleScope.launch(Dispatchers.Main) {
                //while is tracking
                while (stateServices == TRACKING) {
                    //save the last time stamp that is the current time minus the time start
                    lastTimestamp = System.currentTimeMillis() - timeStart
                    //update the time in millis
                    timeInMillis.value = timeRun + lastTimestamp
                    //if the time in millis is greater than the last time in seconds +1000
                    //so will add one to the current time in seconds
                    if (timeInMillis.value >= lastSecondTimestamp + 1000L) {
                        //update the time in seconds
                        timeRunInSeconds += 1
                        notificationServices.updateTimeRunNotification(timeRunInSeconds)
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
        }
    }
}