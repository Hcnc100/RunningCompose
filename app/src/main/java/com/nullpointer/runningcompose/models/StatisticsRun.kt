package com.nullpointer.runningcompose.models

import kotlinx.coroutines.flow.Flow

data class StatisticsRun(
    val distance:Float,
    val AVGSpeed:Float,
    val timeRun:Long,
    val caloriesBurned:Float
)