package io.realworld.jogitup.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("JogTable")
data class RunStats(
    var map :Bitmap?= null,
    var timeStamp : Long = 0L,
    var avgSpeed : Float = 0f,
    var distance : Int = 0,
    var time : Long = 0,
    var calories : Int = 0
){
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}
