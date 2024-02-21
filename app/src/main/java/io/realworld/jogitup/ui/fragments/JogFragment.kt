package io.realworld.jogitup.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_run.*

@AndroidEntryPoint
class JogFragment : Fragment(R.layout.fragment_run) {

    private val viewModel : MainViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }
}