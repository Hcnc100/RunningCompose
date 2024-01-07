package com.nullpointer.runningcompose.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.MapConfig

@Entity(tableName = "run_table")
data class RunEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mapConfig: MapConfig,
    val caloriesBurned: Float,
    val timeRunInMillis: Long,
    val avgSpeedInMeters: Float,
    val distanceInMeters: Float,
    val pathImgRun: String? = null,
    val listPolyLineEncode: List<String>,
    val timestamp: Long = System.currentTimeMillis()
){
    companion object{
        fun fromRunData(runData: RunData):RunEntity{
            return RunEntity(
                mapConfig = runData.mapConfig,
                timestamp = runData.timestamp,
                pathImgRun = runData.pathImgRun,
                caloriesBurned = runData.caloriesBurned,
                timeRunInMillis = runData.timeRunInMillis,
                avgSpeedInMeters = runData.avgSpeedInMeters,
                distanceInMeters = runData.distanceInMeters,
                listPolyLineEncode = runData.listPolyLineEncode,
            )
        }
    }
}