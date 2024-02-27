package io.realworld.jogitup.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realworld.jogitup.database.JogDatabase
import io.realworld.jogitup.extra.Constants.JOG_DB_NAME
import io.realworld.jogitup.extra.Constants.KEY_FIRST
import io.realworld.jogitup.extra.Constants.KEY_NAME
import io.realworld.jogitup.extra.Constants.KEY_WEIGHT
import io.realworld.jogitup.extra.Constants.SHARED_PREF_NAME
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

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext app : Context
    )=app.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME,"")

    @Provides
    @Singleton
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT,80f)

    @Provides
    @Singleton
    fun provideFirstTime(sharedPreferences: SharedPreferences) =
        sharedPreferences.getBoolean(KEY_FIRST,true)
}