package io.realworld.jogitup.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel

@AndroidEntryPoint
class JogFragment : Fragment(R.layout.fragment_run){
    private val viewModel : MainViewModel by viewModels()
}