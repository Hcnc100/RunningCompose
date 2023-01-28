package com.nullpointer.runningcompose.models.types

import androidx.annotation.StringRes
import com.nullpointer.runningcompose.R

enum class SortType(@StringRes val idName:Int,val nameTable:String) {
    DATE(R.string.date_type_name,"timeStamp"),
    RUNNING_TIME(R.string.running_time_type_name,"timeRunInMillis"),
    AVG_SPEED(R.string.avg_speed_type_name,"avgSpeedInMeters"),
    DISTANCE(R.string.distance_type_name,"distanceInMeters"),
    CALORIES_BURNED(R.string.calories_burned_type_name,"caloriesBurned");
}