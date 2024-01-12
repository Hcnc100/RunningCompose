package com.nullpointer.runningcompose.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.clearActionsNotification
import com.nullpointer.runningcompose.core.utils.correctFlag
import com.nullpointer.runningcompose.core.utils.getNotifyManager
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.ui.activitys.MainActivity


/**
 * A helper class for managing notifications in the TrackingServices.
 *
 * @property services The TrackingServices instance that this helper is assisting.
 */
class NotificationHelper(private val services: TrackingServices) {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        private const val NOTIFICATION_ID = 123456789
        private const val ACTION_REQUEST_CODE = 1
        private const val DEEP_LINK_REQUEST_CODE = 0
    }

    private val notifyManager = services.getNotifyManager()
    private val currentNotification by lazy { createBaseNotification() }

    init {
        createChannelNotification()
    }

    /**
     * Creates a notification channel if the Android version is Oreo or above.
     */
    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notifyManager.createNotificationChannel(channel)
        }
    }

    /**
     * Creates a PendingIntent for the action of starting or pausing tracking.
     *
     * @param isTracking Whether tracking is currently active.
     * @return The created PendingIntent.
     */
    private fun createPendingIntentAction(isTracking: Boolean): PendingIntent? {
        val action = when (isTracking) {
            true -> TrackingServices.PAUSE_COMMAND
            false -> TrackingServices.START_OR_RESUME_COMMAND
        }

        val actionIntent = Intent(
            services,
            TrackingServices::class.java
        ).apply { this.action = action }

        return PendingIntent.getService(
            services,
            ACTION_REQUEST_CODE,
            actionIntent,
            services.correctFlag
        )
    }

    /**
     * Creates the base notification builder.
     *
     * @return The created NotificationCompat.Builder.
     */
    private fun createBaseNotification(): NotificationCompat.Builder {
        return NotificationCompat
            .Builder(services, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(services.getString(R.string.app_name))
            .setContentTitle(services.getString(R.string.title_tracking))
            .setContentText("00:00:00")
            .setContentIntent(createPendingIntentCompose())
            .setOnlyAlertOnce(true)
    }

    /**
     * Creates a PendingIntent for the deep link to the tracking page.
     *
     * @return The created PendingIntent.
     */
    private fun createPendingIntentCompose(): PendingIntent? {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.running-compose.com/tracking".toUri(),
            services,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(services).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(DEEP_LINK_REQUEST_CODE, services.correctFlag)
        }
    }

    /**
     * Updates the tracking action in the notification.
     *
     * @param isTracking Whether tracking is currently active.
     */
    fun updateIsTracking(isTracking: Boolean) {
        val textAction = when (isTracking) {
            true -> R.string.text_action_pause
            false -> R.string.text_action_resume
        }
        val pendingIntentAction = createPendingIntentAction(isTracking)
        currentNotification.clearActionsNotification()
        currentNotification.addAction(
            /* icon = */ R.drawable.ic_pause,
            /* title = */ services.getString(textAction),
            /* intent = */ pendingIntentAction
        )
        notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
    }

    /**
     * Starts the foreground service with the current notification.
     */
    fun startRunServices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            services.startForeground(
                NOTIFICATION_ID,
                currentNotification.build(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            services.startForeground(NOTIFICATION_ID, currentNotification.build())
        }
    }

    /**
     * Updates the running time in the notification.
     *
     * @param timeRun The running time in seconds.
     */
    fun updateTimeRun(timeRun: Long) {
        // * update time from notification with the seconds
        currentNotification.setContentText(
            // ? the time run passed as parameter is in seconds so, convert to milliseconds
            (timeRun * 1000L).toFullFormatTime(false)
        )
        notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
    }
}