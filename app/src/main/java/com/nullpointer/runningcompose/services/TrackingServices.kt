package com.nullpointer.runningcompose.services

import android.app.Notification
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
import kotlinx.coroutines.Job
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

        private val listPoints = MutableStateFlow<List<LatLng>>(emptyList())
        val showListPont = listPoints.asStateFlow()

        private val timeInMillis = MutableStateFlow(0L)
        val showTimeInMillis = timeInMillis


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

    private var timerRun = Timer()
    private lateinit var notificationServices: NotificationServices

    private val jobTimer: Job? = null

    override fun onCreate() {
        super.onCreate()
        locationRepository
            .lastLocation
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .filter { stateServices == TRACKING }
            .onEach {
                Timber.d("LOcation send to tackin services")
                listPoints.value = listPoints.value + it
            }
            .onCompletion {
                listPoints.value = emptyList()
                timerRun = Timer()
                Timber.d("LOaction cancelled")
            }
            .launchIn(lifecycleScope)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.action?.let {
            when (it) {
                START_COMMAND -> {
                    Timber.d("Services init")
                    notificationServices = NotificationServices()
                    stateServices = TRACKING
                    notificationServices.startRunServices()
                    timerRun.startTimer()
                }
                PAUSE_OR_RESUME_COMMAND -> {
                    stateServices = if (stateServices == TRACKING) {
                        notificationServices.updateAction(false)
                        PAUSE
                    } else {
                        notificationServices.updateAction(true)
                        TRACKING
                    }
                    timerRun.startTimer()
                }
                STOP_COMMAND -> {
                    Timber.d("Finish services")
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

        private val notifyManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        private val context get() = this@TrackingServices
        private val currentNotification: NotificationCompat.Builder

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannelNotification()
            }
            currentNotification = createBaseNotification()
        }

        private fun createChannelNotification() {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notifyManager.createNotificationChannel(channel)
        }

        private fun getPendingIntentCompose(): PendingIntent? {
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

        private fun createBaseNotification(): NotificationCompat.Builder {
            return NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(context.getString(R.string.app_name))
                .setContentText("00:00:00:00")
                .setContentIntent(getPendingIntentCompose())
        }

        fun updateAction(isTracking: Boolean) {

            currentNotification.clearActionsNotification()

            val textAction = if (isTracking) "Pause" else "Resumen"

            val actionIntent = Intent(
                context,
                TrackingServices::class.java
            ).apply { action = PAUSE_OR_RESUME_COMMAND }

            val pendingIntent = PendingIntent.getService(
                this@TrackingServices,
                1,
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            currentNotification.addAction(
                R.drawable.ic_pause,
                textAction,
                pendingIntent
            )

            notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
        }

        fun startRunServices() {
            startForeground(NOTIFICATION_ID, currentNotification.build())
            updateAction(true)
        }

        fun updateTimeRunNotification(timeRun: Long) {
            currentNotification.setContentText(
                timeRun.toFullFormatTime(false)
            )
            notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
        }

    }


    private inner class Timer {

        // * var to save the timestamp in milliseconds (for update text where show full time)
        private var lastTimestampInMillis = 0L

        // * var to save the timestamp without milliseconds (for update text in the notification)
        private var lastTimestampInSeconds = 0L

        // * var to save all timestamp in milliseconds
        private var allTimeRun = 0L

        fun startTimer() {
            val timeStart = System.currentTimeMillis()
            lifecycleScope.launch {
                while (stateServices == TRACKING) {
                    // * calculate the time elapsed since the start of the timer
                    lastTimestampInMillis = System.currentTimeMillis() - timeStart

                    timeInMillis.value = lastTimestampInMillis + allTimeRun

                    if (lastTimestampInMillis >= lastTimestampInSeconds + 1000L) {
                        lastTimestampInSeconds += 1000L
                        notificationServices.updateTimeRunNotification(lastTimestampInSeconds)
                    }

                    delay(TIMER_DELAY_TIMER)
                }
                // * update all time run
                allTimeRun += lastTimestampInMillis
            }
        }
    }
}