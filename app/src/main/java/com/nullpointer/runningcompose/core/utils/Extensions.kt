package com.nullpointer.runningcompose.core.utils

import android.content.Context
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.location.LocationRequestCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.MetricType.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toFullFormat(context: Context): String {
    val base = "dd/MM/yyyy hh:mm"
    val newPattern = if (DateFormat.is24HourFormat(context)) base else "$base a"
    val sdf = SimpleDateFormat(newPattern, Locale.getDefault())
    return sdf.format(this)
}

fun Long.toDateOnlyTime(context: Context): String {
    val base = "hh:mm"
    val newPattern = if (DateFormat.is24HourFormat(context)) base else "$base a"
    val sdf = SimpleDateFormat(newPattern, Locale.getDefault())
    return sdf.format(this)
}

fun Long.toDateFormat(): String {
    val base = "dd/MM/yyyy"
    val sdf = SimpleDateFormat(base, Locale.getDefault())
    return sdf.format(this)
}

fun Float.toMeters(metricType: MetricType, precision: Int = 2) =
    when (metricType) {
        Meters -> "%.${precision}f m".format(this)
        Kilo -> "%.${precision}f km".format(this / 1000f)
    }


fun Float.toAVGSpeed(metricType: MetricType, precision: Int = 2): String =
    when (metricType) {
        Meters -> "%.${precision}f m/s".format(this)
        Kilo -> "%.${precision}f km/h".format(this * 3.6)
    }


fun Float.toCaloriesBurned(metricType: MetricType, precision: Int = 2): String =
    when (metricType) {
        Meters -> "%.${precision}f cal".format(this)
        Kilo -> "%.${precision}f kcal".format(this / 1000)
    }

fun Long.toFullFormatTime(includeMillis: Boolean): String {
    var milliseconds = this
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    milliseconds -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
    if (!includeMillis) {
        return "${if (hours < 10) "0" else ""}$hours:" +
                "${if (minutes < 10) "0" else ""}$minutes:" +
                "${if (seconds < 10) "0" else ""}$seconds"
    }
    milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
    milliseconds /= 10
    return "${if (hours < 10) "0" else ""}$hours:" +
            "${if (minutes < 10) "0" else ""}$minutes:" +
            "${if (seconds < 10) "0" else ""}$seconds:" +
            "${if (milliseconds < 10) "0" else ""}$milliseconds"
}
fun Context.getPlural(@PluralsRes stringQuality: Int,quality:Int): String {
    return resources.getQuantityString(stringQuality,quality,quality)
}

//get fields actions and remove all using empty array
fun NotificationCompat.Builder.clearActionsNotification() {
    javaClass.getDeclaredField("mActions").apply {
        isAccessible = true
        set(this@clearActionsNotification, ArrayList<NotificationCompat.Action>())
    }
}

@Composable
inline fun <reified VM : ViewModel> shareViewModel():VM {
    val activity= LocalContext.current as ComponentActivity
    return hiltViewModel(activity)
}