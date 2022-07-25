package com.nullpointer.runningcompose.services

import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.clearActionsNotification
import com.nullpointer.runningcompose.core.utils.correctFlag
import com.nullpointer.runningcompose.core.utils.getNotifyManager
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.ui.activitys.MainActivity

class NotificationHelper(private val services: TrackingServices) {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Tracking"
        private const val NOTIFICATION_ID = 123456789
    }

    private val notifyManager = services.getNotifyManager()
    private val currentNotification by lazy { createBaseNotification() }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelNotification()
        }
    }

    private fun createChannelNotification() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_HIGH
        )
        notifyManager.createNotificationChannel(channel)
    }

    private fun createPendingIntentAction(isTracking: Boolean): PendingIntent? {
        val action =
            if (isTracking) TrackingServices.PAUSE_COMMAND else TrackingServices.START_OR_RESUME_COMMAND

        val actionIntent = Intent(
            services,
            TrackingServices::class.java
        ).apply { this.action = action }

        return PendingIntent.getService(
            services,
            1,
            actionIntent,
            services.correctFlag
        )
    }

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

    private fun createPendingIntentCompose(): PendingIntent? {
        // * create deep link
        // * this go to post for notification
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            "https://www.running-compose.com/tracking".toUri(),
            services,
            MainActivity::class.java
        )
        // * create pending intent compose
        val deepLinkPendingIntent = TaskStackBuilder.create(services).run {
            addNextIntentWithParentStack(deepLinkIntent)
            getPendingIntent(0, services.correctFlag)
        }
        return deepLinkPendingIntent
    }

    fun updateIsTracking(isTracking: Boolean) {
        val textAction = if (isTracking) R.string.text_action_pause else R.string.text_action_resume
        val pendingIntentAction = createPendingIntentAction(isTracking)
        currentNotification.clearActionsNotification()
        currentNotification.addAction(
            R.drawable.ic_pause,
            services.getString(textAction),
            pendingIntentAction
        )
        notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
    }

    fun startRunServices() {
        services.startForeground(NOTIFICATION_ID, currentNotification.build())
    }

    fun updateTimeRun(timeRun: Long) {
        // * update time from notification with the seconds
        currentNotification.setContentText(
            // ? the time run passed as parameter is in seconds so, convert to milliseconds
            (timeRun * 1000L).toFullFormatTime(false)
        )
        notifyManager.notify(NOTIFICATION_ID, currentNotification.build())
    }
}