package io.realworld.jogitup.database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities =  [RunStats::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class JogDatabase : RoomDatabase() {
    abstract fun getDao() : StatsDao
}