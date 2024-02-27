package io.realworld.jogitup.extra

import android.content.Context
import android.location.Location
import io.realworld.jogitup.services.Polyline
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

    fun calculatePolylineLen(polyline: Polyline) :Float{
        var distance = 0f
        for(i in 0..polyline.size - 2){
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]

            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
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
                    "${if(seconds < 10)"0" else ""}$seconds"
        }
        milliSec -= TimeUnit.SECONDS.toMillis(seconds)
        milliSec /= 10
        return "${if(hours < 10)"0" else ""}$hours:" +
                "${if(minutes < 10)"0" else ""}$minutes:" +
                "${if(seconds < 10)"0" else ""}$seconds:" +
                "${if(milliSec < 10)"0" else ""}$milliSec"
    }

}