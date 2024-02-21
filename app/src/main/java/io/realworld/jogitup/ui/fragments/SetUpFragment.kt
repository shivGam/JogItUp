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
import kotlinx.android.synthetic.main.fragment_setup.*

class SetUpFragment : Fragment(R.layout.fragment_setup){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvContinue.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
    }
}