package io.realworld.jogitup.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realworld.jogitup.MainRepository
import io.realworld.jogitup.database.RunStats
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository: MainRepository
):ViewModel(){
    val runSortedByDate = mainRepository.getAllByDates()

    fun insert(run :RunStats) = viewModelScope.launch {
        mainRepository.insert(run)
    }
}