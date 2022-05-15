package com.nullpointer.runningcompose.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullpointer.runningcompose.models.Run

@Database(
    entities = [Run::class],
    version = 1,
    exportSchema = false
)
abstract class RunDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDAO

}