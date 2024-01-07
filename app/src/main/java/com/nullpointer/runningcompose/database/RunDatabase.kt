package com.nullpointer.runningcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nullpointer.runningcompose.models.Run

@Database(
    entities = [Run::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverts::class)
abstract class RunDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDAO

}