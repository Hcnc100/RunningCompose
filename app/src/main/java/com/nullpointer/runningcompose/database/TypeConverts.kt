package com.nullpointer.runningcompose.database

import androidx.room.TypeConverter
import com.nullpointer.runningcompose.models.data.config.MapConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object TypeConverts {

    @TypeConverter
    fun listToJson(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> = Json.decodeFromString(value)

    @TypeConverter
    fun mapConfigToString(mapConfig: MapConfig): String = Json.encodeToString(mapConfig)

    @TypeConverter
    fun stringToMapConfig(value: String): MapConfig = Json.decodeFromString(value)
}