package io.realworld.jogitup.extra

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import io.realworld.jogitup.database.RunStats
import kotlinx.android.synthetic.main.marker_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class CustomMakerView (
    val runStats: List<RunStats>,
    context: Context,
    layoutId : Int
):MarkerView(context,layoutId){

    override fun getOffset(): MPPointF {
        return MPPointF(-width/2f,-height.toFloat())
    }
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if(e==null){
            return
        }
        val curRunId = e.x.toInt()
        val run = runStats[curRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timeStamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        tvDate.text = dateFormat.format(calendar.time)
        val avgSpeed = "${run.avgSpeed}Km/h"
        tvAvgSpeed.text = avgSpeed
        val distance = "${run.distance/1000f}km"
        tvDistance.text = distance

        tvDuration.text = Utility.formatTime(run.time)

        val caloriesBurned = "${run.calories}Kcal"
        tvCaloriesBurned.text = caloriesBurned
    }
}