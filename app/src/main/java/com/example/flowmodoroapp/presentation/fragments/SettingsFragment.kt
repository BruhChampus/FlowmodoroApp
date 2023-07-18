package com.example.flowmodoroapp.presentation.fragments

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources.Theme
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.data.MySharedPreference
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

        val mySharedPreference = MySharedPreference(requireActivity())

        binding.scSwitchNightMode.isChecked = mySharedPreference.getSharedPref()


        binding.scSwitchNightMode.setOnCheckedChangeListener{_, isChecked ->
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO && isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                findNavController().navigate(R.id.settingsFragment)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                findNavController().navigate(R.id.settingsFragment)
            }
        }

        mySharedPreference.saveInSharedPref(binding.scSwitchNightMode.isChecked)

        binding.ivYellow.setOnClickListener {
            requireContext().setTheme(R.style.Theme_FlowmodoroAppYellow)
            findNavController().navigate(R.id.settingsFragment)
         }

        binding.ivWhite.setOnClickListener {
            requireContext().setTheme(R.style.Theme_FlowmodoroAppWhite)
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.ivRed.setOnClickListener {
            requireContext().setTheme(R.style.Theme_FlowmodoroApp)
            findNavController().navigate(R.id.settingsFragment)
        }
        return binding.root
    }





}