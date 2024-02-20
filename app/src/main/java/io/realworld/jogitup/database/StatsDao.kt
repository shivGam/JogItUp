package io.realworld.jogitup.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface StatsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(runStats: RunStats)

    @Delete
    suspend fun delete(runStats: RunStats)

    @Query("SELECT * FROM JogTable ORDER BY timestamp DESC")
    fun getAllRunsTimestamp() : LiveData<List<RunStats>>

    @Query("SELECT * FROM JogTable ORDER BY avgSpeed DESC")
    fun getAllRunsAvgSpeed() : LiveData<List<RunStats>>

    @Query("SELECT * FROM JogTable ORDER BY distance DESC")
    fun getAllRunsDistance() : LiveData<List<RunStats>>

    @Query("SELECT * FROM JogTable ORDER BY time DESC")
    fun getAllRunsTime() : LiveData<List<RunStats>>

    @Query("SELECT * FROM JogTable ORDER BY calories DESC")
    fun getAllRunsCalories() : LiveData<List<RunStats>>

    @Query("SELECT SUM(time) FROM JogTable")
    fun getTotalTime() : LiveData<Long>

    @Query("SELECT SUM(distance) FROM JogTable")
    fun getTotalDistance() : LiveData<Int>

    @Query("SELECT SUM(calories) FROM JogTable")
    fun getTotalCalories() : LiveData<Int>

    @Query("SELECT AVG(avgSpeed) FROM JogTable")
    fun getTotalAvgSpeed() : LiveData<Float>

}