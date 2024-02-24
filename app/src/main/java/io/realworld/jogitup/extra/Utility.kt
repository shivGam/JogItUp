package io.realworld.jogitup.extra

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import java.util.concurrent.TimeUnit

object Utility {
    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    fun formatTime(ms : Long , includeMillis:Boolean = false):String {
        var milliSec = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliSec)
        milliSec -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliSec)
        milliSec -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliSec)
        if(!includeMillis){
            return "${if(hours < 10)"0" else ""}$hours:" +
                    "${if(minutes < 10)"0" else ""}$minutes:" +
                    "${if(seconds < 10)"0" else ""}$seconds:"
        }
        milliSec -= TimeUnit.SECONDS.toMillis(seconds)
        milliSec /= 10
        return "${if(hours < 10)"0" else ""}$hours:" +
                "${if(minutes < 10)"0" else ""}$minutes:" +
                "${if(seconds < 10)"0" else ""}$seconds:" +
                "${if(milliSec < 10)"0" else ""}$milliSec"
    }

}