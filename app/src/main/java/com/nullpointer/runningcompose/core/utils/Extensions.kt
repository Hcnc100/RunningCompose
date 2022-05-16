package com.nullpointer.runningcompose.core.utils

import android.content.Context
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toFullFormat(context: Context): String {
    val base = "EEEE dd/MM/yyyy hh:mm"
    val newPattern = if (DateFormat.is24HourFormat(context)) base else "$base a"
    val sdf = SimpleDateFormat(newPattern, Locale.getDefault())
    return sdf.format(this)
}

fun Long.toDateFormat(): String {
    val base = "dd/MM/yyyy"
    val sdf = SimpleDateFormat(base, Locale.getDefault())
    return sdf.format(this)
}

fun Float.toMeters(isInMeters: Boolean, precision: Int = 2): String =
    if (isInMeters) "%.${precision}f m".format(this)
    else "%.${precision}f km".format(this / 1000f)

fun Float.toAVGSpeed(isInMeters: Boolean, precision: Int = 2): String =
    if (isInMeters) "%.${precision}f m/s".format(this)
    else "%.${precision}f km/h".format(this * 3.6)

fun Float.toCaloriesBurned(isInMeters: Boolean, precision: Int = 2): String =
    if (isInMeters) "%.${precision}f cal".format(this)
    else "%.${precision}f kcal".format(this / 1000)

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