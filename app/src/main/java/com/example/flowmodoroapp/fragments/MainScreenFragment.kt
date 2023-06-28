package com.example.flowmodoroapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.flowmodoroapp.MainScreenFragmentDirections
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater)

        binding.ivStart.setOnClickListener {
            val action =
                MainScreenFragmentDirections.actionMainScreenFragmentToStudyingScreenFragment(
                    binding.etTaskName.text.toString()
                )
            it.findNavController().navigate(action)
        }
        binding.ivSettings.setOnClickListener {
            it.findNavController().navigate(R.id.settingsFragment)
        }

        binding.ivResults.setOnClickListener {
            it.findNavController().navigate(R.id.resultsScreenFragment)
        }

        binding.flAppLogo.setOnClickListener {
            Toast.makeText(requireContext(), "Hello there!", Toast.LENGTH_SHORT).show()
            //TODO with help of coroutines add delay
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { requireActivity().finish() }
        // Inflate the layout for this fragment
        return binding.root
    }
}