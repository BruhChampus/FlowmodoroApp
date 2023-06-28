package com.example.flowmodoroapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.databinding.FragmentResultsScreenBinding


class ResultsScreenFragment : Fragment() {

    private lateinit var binding:FragmentResultsScreenBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultsScreenBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.ivClose.setOnClickListener {
            it.findNavController().navigate(R.id.mainScreenFragment)
        }
        binding.ivSettings.setOnClickListener {
            it.findNavController().navigate(R.id.settingsFragment)
        }

        return binding.root
    }


}