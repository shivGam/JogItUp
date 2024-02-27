package io.realworld.jogitup.ui.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.room.Insert
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.extra.Constants.KEY_FIRST
import io.realworld.jogitup.extra.Constants.KEY_NAME
import io.realworld.jogitup.extra.Constants.KEY_WEIGHT
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_setup.*
import javax.inject.Inject

@AndroidEntryPoint
class SetUpFragment : Fragment(R.layout.fragment_setup){

    @Inject
    lateinit var sharedPref : SharedPreferences

    @set:Inject
    var isFirstOpen = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isFirstOpen){
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment,true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }
        tvContinue.setOnClickListener {
            val success = writeDataIntoSharedPref()
            if(success){
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }else{
                Snackbar.make(
                    requireView(),
                    "Please Enter all Fields",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun writeDataIntoSharedPref():Boolean{
        val name = etName.text.toString()
        val weight = etWeight.text.toString()
        if(name.isEmpty() || weight.isEmpty()){
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME,name)
            .putFloat(KEY_WEIGHT,weight.toFloat())
            .putBoolean(KEY_FIRST,false)
            .apply()
        return true
    }
}