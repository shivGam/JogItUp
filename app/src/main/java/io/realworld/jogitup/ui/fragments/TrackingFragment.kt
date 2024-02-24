package io.realworld.jogitup.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.extra.Constants.ACTION_PAUSE_SERVICE
import io.realworld.jogitup.extra.Constants.ACTION_START_OR_RESUME_SERVICE
import io.realworld.jogitup.extra.Constants.LINE_COLOUR
import io.realworld.jogitup.extra.Constants.LINE_WIDTH
import io.realworld.jogitup.extra.Constants.MAP_ZOOM
import io.realworld.jogitup.extra.Utility
import io.realworld.jogitup.services.Polyline
import io.realworld.jogitup.services.TrackingService
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking){
    private val viewModel : MainViewModel by viewModels()
    private var map : GoogleMap? = null
    private var curTimeMillis = 0L
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToggleRun.setOnClickListener {
            toggleRun()
        }
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            addAllPolyes()
        }
        subsToObserver()
    }

    private fun subsToObserver(){
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })
        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            moveToCenter()
        })
        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            curTimeMillis = it
            val formatTime = Utility.formatTime(curTimeMillis,true)
            tvTimer.text = formatTime
        })
    }

    private fun toggleRun() = sendCommand(if (isTracking) ACTION_PAUSE_SERVICE else ACTION_START_OR_RESUME_SERVICE)

    private fun updateTracking(isTracking : Boolean){
        this.isTracking = isTracking
        if(!isTracking){
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        }else{
            btnToggleRun.text = "STOP"
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun moveToCenter(){
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun addAllPolyes(){
        for(polyline in pathPoints){
            val polylineOp = PolylineOptions()
                .color(LINE_COLOUR)
                .width(LINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOp)
        }
    }

    private fun addLatestPolyline() {
        if(pathPoints.isNotEmpty()&& pathPoints.last().size > 1){
            val preLast = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOp = PolylineOptions()
                .color(LINE_COLOUR)
                .width(LINE_WIDTH)
                .add(preLast)
                .add(lastLatLng)
            map?.addPolyline(polylineOp)
        }
    }

    private fun sendCommand(action:String) =
        Intent(requireContext(),TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}