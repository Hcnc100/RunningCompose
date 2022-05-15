package com.nullpointer.runningcompose.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nullpointer.runningcompose.models.Run
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDAO {

    @Query("SELECT * FROM run_table ORDER BY timeStamp DESC")
    fun getListRunsByTimestamp(): Flow<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurned DESC")
    fun getListRunsByCaloriesBurned(): Flow<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY distance DESC")
    fun getListRunsByDistance(): Flow<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY avgSpeed DESC")
    fun getListRunsByAvgSpeed(): Flow<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeRunInMillis DESC")
    fun getListRunsByRunTimeRun(): Flow<List<Run>>

    @Insert
    suspend fun insertNewRun(run: Run)

    @Delete
    suspend fun deleterRun(run: Run)

    @Query("DELETE FROM run_table WHERE id IN (:listIds)")
    suspend fun deleterListIdRuns(listIds: List<Long>)
}