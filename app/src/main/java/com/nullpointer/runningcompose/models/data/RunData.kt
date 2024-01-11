package com.nullpointer.runningcompose.models.data

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
    val createAt: Long = System.currentTimeMillis()
){
    companion object{
        val runDataExample = RunData(
            0,
            mapConfig = MapConfig(),
            caloriesBurned = 100F,
            timeRunInMillis = 53000,
            pathImgRun = null,
            avgSpeedInMeters = 150F,
            distanceInMeters = 2500F,
            listPolyLineEncode = emptyList(),
            createAt = 100023
        )

        val listRunsExample = listOf(
            RunData(
                0,
                mapConfig = MapConfig(),
                caloriesBurned = 100F,
                timeRunInMillis = 53000,
                pathImgRun = null,
                avgSpeedInMeters = 150F,
                distanceInMeters = 2500F,
                listPolyLineEncode = emptyList(),
                createAt = 100023
            ),
            RunData(
                1,
                mapConfig = MapConfig(),
                caloriesBurned = 200F,
                timeRunInMillis = 53020,
                pathImgRun = null,
                avgSpeedInMeters = 130F,
                distanceInMeters = 2500F,
                listPolyLineEncode = emptyList(),
                createAt = 100023
            ),
            RunData(
                2,
                mapConfig = MapConfig(),
                caloriesBurned = 2500F,
                timeRunInMillis = 51000,
                pathImgRun = null,
                avgSpeedInMeters = 123F,
                distanceInMeters = 2500F,
                listPolyLineEncode = emptyList(),
                createAt = 100023
            ),
            RunData(
                3,
                mapConfig = MapConfig(),
                caloriesBurned = 100F,
                timeRunInMillis = 53000,
                pathImgRun = null,
                avgSpeedInMeters = 320F,
                distanceInMeters = 2500F,
                listPolyLineEncode = emptyList(),
                createAt = 100023
            )
        )

        fun fromRunEntity(runEntity: RunEntity): RunData {
            return RunData(
                id = runEntity.id,
                mapConfig = runEntity.mapConfig,
                createAt = runEntity.timestamp,
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