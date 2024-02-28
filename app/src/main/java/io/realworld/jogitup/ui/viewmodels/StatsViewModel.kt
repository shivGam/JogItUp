package io.realworld.jogitup.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realworld.jogitup.MainRepository
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    val mainRepository: MainRepository
):ViewModel(){

    val totalTimeRun = mainRepository.getTotalTime()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCalories()
    val totalAvgSpeed = mainRepository.getAvgSpeed()

    val runSortedByDate = mainRepository.getAllByDates()
}