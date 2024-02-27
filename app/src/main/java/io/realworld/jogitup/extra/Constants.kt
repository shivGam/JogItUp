package io.realworld.jogitup.extra

import android.graphics.Color

object Constants {
    const val JOG_DB_NAME = "jog_db"
    const val REQUEST_PERMISSION_CODE = 0

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val SHARED_PREF_NAME = "sharedPref"
    const val KEY_NAME = "KEY_NAME"
    const val KEY_WEIGHT = "KEY_WEIGHT"
    const val KEY_FIRST = "KEY_FIRST"

    const val UPDATE_INTERVAL = 50L
    const val LOCATION_UPDATE_INTERVAL = 5000L
    const val FASTEST_LOCATION_INTERVAL = 2000L

    const val LINE_COLOUR = Color.RED
    const val LINE_WIDTH = 15f
    const val MAP_ZOOM = 18f

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID =  1
}