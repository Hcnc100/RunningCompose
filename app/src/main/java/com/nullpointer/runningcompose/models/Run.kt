package com.nullpointer.runningcompose.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nullpointer.runningcompose.models.config.MapConfig

@Entity(tableName = "run_table")
@kotlinx.serialization.Serializable
data class Run(
    val avgSpeedInMeters: Float,
    val distanceInMeters: Float,
    val timeRunInMillis: Long,
    val caloriesBurned: Float,
    val listPolyLineEncode: List<String>,
    val timestamp: Long = System.currentTimeMillis(),
    val mapConfig: MapConfig,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pathImgRun: String? = null
)