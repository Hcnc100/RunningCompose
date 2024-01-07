package com.nullpointer.runningcompose.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow


@Dao
interface RunDAO {
    @RawQuery(observedEntities = [Run::class])
    fun getListRunRawQuery(query: SupportSQLiteQuery): PagingSource<Int, Run>

    fun getListRunsBy(sortType: SortType, ascendance: Boolean): PagingSource<Int, Run> {
        val order = if (ascendance) "ASC" else "DESC"
        val statement = "SELECT * FROM run_table ORDER BY ${sortType.nameTable} $order"
        val query: SupportSQLiteQuery = SimpleSQLiteQuery(statement, arrayOf())
        return getListRunRawQuery(query)
    }
    @Query("SELECT * FROM run_table ORDER by timeStamp ASC LIMIT :limit")
    fun getLastRunsOrderByDate(limit:Int):Flow<List<Run>>

    @Insert
    suspend fun insertNewRun(run: Run)

    @Delete
    suspend fun deleterRun(run: Run)

    @Query("DELETE FROM run_table WHERE id IN (:listIds)")
    suspend fun deleterListRuns(listIds: List<Long>)

    @Query("SELECT * FROM run_table WHERE id IN (:listIds)")
    fun getListRunsById(listIds: List<Long>): List<Run>

    @Query("SELECT TOTAL(timeRunInMillis) FROM run_table")
    fun getTotalTimeInMillis(): Flow<Long>

    @Query("SELECT TOTAL(caloriesBurned) FROM run_table")
    fun getTotalCaloriesBurned(): Flow<Float>

    @Query("SELECT TOTAL(distanceInMeters) FROM run_table")
    fun getTotalDistance(): Flow<Float>

    @Query("SELECT AVG(avgSpeedInMeters) FROM run_table")
    fun getTotalAVGSpeed(): Flow<Float?>

    @Query("SELECT COUNT(*) FROM run_table")
    fun getRowCount():Flow<Int>
}