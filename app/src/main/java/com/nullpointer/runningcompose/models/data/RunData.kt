package com.nullpointer.runningcompose.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.entities.RunEntity

@kotlinx.serialization.Serializable
data class RunData(
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
        fun fromRunEntity(runEntity: RunEntity): RunData {
            return RunData(
                id = runEntity.id,
                mapConfig = runEntity.mapConfig,
                timestamp = runEntity.timestamp,
                pathImgRun = runEntity.pathImgRun,
                caloriesBurned = runEntity.caloriesBurned,
                timeRunInMillis = runEntity.timeRunInMillis,
                avgSpeedInMeters = runEntity.avgSpeedInMeters,
                distanceInMeters = runEntity.distanceInMeters,
                listPolyLineEncode = runEntity.listPolyLineEncode,
            )
        }
    }
}