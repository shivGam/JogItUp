package io.realworld.jogitup.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realworld.jogitup.database.JogDatabase
import io.realworld.jogitup.extra.Constants.JOG_DB_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJogStatsDb(
        @ApplicationContext app : Context
    )= Room.databaseBuilder(
        app,
        JogDatabase::class.java,
        JOG_DB_NAME
    ).build()

    @Provides
    @Singleton
    fun provideStatsDao(database: JogDatabase) = database.getDao()

}