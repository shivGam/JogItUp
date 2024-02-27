package io.realworld.jogitup.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.database.RunStats
import io.realworld.jogitup.extra.Constants.ACTION_PAUSE_SERVICE
import io.realworld.jogitup.extra.Constants.ACTION_START_OR_RESUME_SERVICE
import io.realworld.jogitup.extra.Constants.ACTION_STOP_SERVICE
import io.realworld.jogitup.extra.Constants.LINE_COLOUR
import io.realworld.jogitup.extra.Constants.LINE_WIDTH
import io.realworld.jogitup.extra.Constants.MAP_ZOOM
import io.realworld.jogitup.extra.Utility
import io.realworld.jogitup.services.Polyline
import io.realworld.jogitup.services.TrackingService
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.Calendar
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking),MenuProvider{
    private val viewModel : MainViewModel by viewModels()
    private var map : GoogleMap? = null
    private var curTimeMillis = 0L
    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    private var menu : Menu? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.addMenuProvider(this,viewLifecycleOwner)

        btnToggleRun.setOnClickListener {
            toggleRun()
        }
        btnFinishRun.setOnClickListener {
            zoomToFit()
            saveRun()
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

    private fun toggleRun() {
        if(isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommand(ACTION_PAUSE_SERVICE)
        }else{
            sendCommand(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun stopRun() {
        sendCommand(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }

    private fun updateTracking(isTracking : Boolean){
        this.isTracking = isTracking
        if(!isTracking){
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        }else{
            btnToggleRun.text = "STOP"
            menu?.getItem(0)?.isVisible = true
            btnFinishRun.visibility = View.GONE
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        this.menu = menu
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        if(curTimeMillis>0L){
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.cancel_button -> {
                cancelRunDialog()
                return true
            }
        }
        return false
    }

    private fun cancelRunDialog(){
        val dialog = MaterialAlertDialogBuilder(requireContext(), androidx.appcompat.R.style.AlertDialog_AppCompat)
            .setTitle("Cancel the Jog")
            .setMessage("Are you Sure to Terminate run?")
            .setIcon(R.drawable.ic_cancel)
            .setPositiveButton("Yes"){ _,_ ->
                stopRun()
            }
            .setNegativeButton("No"){ dialogInterface,_ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
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

    private fun zoomToFit(){
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints){
            for(pos in polyline){
                bounds.include(pos)
            }
        }
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.10f).toInt()
            )
        )
    }

    private fun saveRun(){
        map?.snapshot { bmp ->
            var distanceInMeter = 0
            for(polyline in pathPoints){
                distanceInMeter += Utility.calculatePolylineLen(polyline).toInt()
            }
            val avgSpeed = round((distanceInMeter/1000f)/(curTimeMillis/100f/60/60) * 10)/10f
            val dataTime = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeter/1000f)*80f).toInt()
            val run = RunStats(bmp,dataTime,avgSpeed,distanceInMeter,curTimeMillis,caloriesBurned)
            viewModel.insert(run)
            Snackbar.make(
                requireActivity().findViewById(R.id.rootView),
                "Run Saved Successfully",
                Snackbar.LENGTH_LONG
            ).show()
            stopRun()
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