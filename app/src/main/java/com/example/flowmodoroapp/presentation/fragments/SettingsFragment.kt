package com.example.flowmodoroapp.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.ivClose.setOnClickListener {
            it.findNavController().navigate(R.id.mainScreenFragment)
        }
        binding.ivResults.setOnClickListener {
            it.findNavController().navigate(R.id.resultsScreenFragment)
        }

        binding.scSwitchNightMode.setOnCheckedChangeListener{buttonview, isChecked ->
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO && isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return binding.root
    }


}