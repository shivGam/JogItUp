package io.realworld.jogitup.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.jogitup.R
import io.realworld.jogitup.extra.Constants.KEY_NAME
import io.realworld.jogitup.extra.Constants.KEY_WEIGHT
import io.realworld.jogitup.ui.viewmodels.MainViewModel
import io.realworld.jogitup.ui.viewmodels.StatsViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_setup.*
import kotlinx.android.synthetic.main.fragment_setup.etName
import kotlinx.android.synthetic.main.fragment_setup.etWeight
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings){

    @Inject
    lateinit var sharedPref : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFromSharedPref()
        btnApplyChanges.setOnClickListener {
            val success = changesInSharedPref()
            if(success){
                Snackbar.make(
                    view,
                    "Saved Changes",
                    Snackbar.LENGTH_SHORT
                ).show()
            }else {
                Snackbar.make(
                    view,
                    "Fill all Fields",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadFromSharedPref() {
        val name = sharedPref.getString(KEY_NAME,"")
        val weight = sharedPref.getFloat(KEY_WEIGHT,80f)
        etName.setText(name)
        etWeight.setText(weight.toString())
    }

    private fun changesInSharedPref():Boolean{
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()
        if(nameText.isEmpty() || weightText.isEmpty()){
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME,nameText)
            .putFloat(KEY_WEIGHT,weightText.toFloat())
            .apply()
        return true
    }

}