package io.realworld.jogitup.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.extra.CustomMakerView
import io.realworld.jogitup.extra.Utility
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.item_run.*
import kotlin.math.round

@AndroidEntryPoint
class StatsFragment : Fragment(R.layout.fragment_statistics){

    private val viewModel : StatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subsToObserves()
        setUpBarChart()
    }

    private fun setUpBarChart(){
        barChart.xAxis.apply {
            setDrawLabels(false)
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
        }
        barChart.axisLeft.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        barChart.axisRight.apply {
            axisLineColor = Color.WHITE
            textColor = Color.WHITE
            setDrawGridLines(false)
        }
        barChart.apply {
            description.text = "Avg Speed Over Time"
            legend.isEnabled = false
        }
    }

    private fun subsToObserves() {
        viewModel.totalTimeRun.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalTime = Utility.formatTime(it)
                tvTotalTime.text = totalTime
            }
        })
        viewModel.totalDistance.observe(viewLifecycleOwner, Observer {
            it?.let {
                val km = it/1000f
                val totalDistance =  round(km*10f)/10f
                val totalDistanceString = "${totalDistance}km"
                tvTotalDistance.text = totalDistanceString
            }
        })
        viewModel.totalAvgSpeed.observe(viewLifecycleOwner, Observer {
            it?.let {
                val avgSpeed = round(it*10f)/10f
                val totalAvgSpeedString = "${avgSpeed}km/h"
                tvAverageSpeed.text = totalAvgSpeedString
            }
        })
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner, Observer {
            it?.let {
                val totalCalories = "${it}kcal"
                tvTotalCalories.text = totalCalories
            }
        })
        viewModel.runSortedByDate.observe(viewLifecycleOwner, Observer {
            it?.let{
                val allAvgSpeed = it.indices.map { i -> BarEntry(i.toFloat(),it[i].avgSpeed) }
                val barDataset = BarDataSet(allAvgSpeed,"Avg Speed Over Time").apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), pub.devrel.easypermissions.R.color.colorAccent)
                }
                barChart.data = BarData(barDataset)
                barChart.marker = CustomMakerView(it.reversed(),requireContext(),R.layout.marker_view)
                barChart.invalidate()
            }
        })
    }
}