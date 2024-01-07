package com.nullpointer.runningcompose.models.data

import com.nullpointer.runningcompose.models.entities.RunEntity

data class StatisticsData(
    val lastRunEntities: List<RunEntity>,
    val totalStatisticRuns: StatisticsRun
)
