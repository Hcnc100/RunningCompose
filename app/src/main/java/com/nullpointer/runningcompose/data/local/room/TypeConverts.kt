package com.nullpointer.runningcompose.data.local.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

object TypeConverts {
    private val gson = Gson()

    @TypeConverter
    fun listToJson(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = gson.fromJson(value, Array<String>::class.java).toList()
}