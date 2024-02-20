package io.realworld.jogitup

import io.realworld.jogitup.database.RunStats
import io.realworld.jogitup.database.StatsDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    val dao: StatsDao
) {
    suspend fun insert(runStats: RunStats) = dao.insert(runStats)

    suspend fun delete(runStats: RunStats) = dao.delete(runStats)

    fun getAllByDates() = dao.getAllRunsTimestamp()

    fun getAllByAvgSpeed() = dao.getAllRunsAvgSpeed()

    fun getAllByCalories() = dao.getAllRunsCalories()

    fun getAllByDistance() = dao.getAllRunsDistance()

    fun getTotalTime() = dao.getTotalTime()

    fun getAvgSpeed() = dao.getTotalAvgSpeed()

    fun getTotalCalories() = dao.getTotalCalories()

    fun getTotalDistance() = dao.getTotalDistance()
}