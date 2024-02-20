package io.realworld.jogitup

import android.app.Application
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import io.realworld.jogitup.database.StatsDao
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}