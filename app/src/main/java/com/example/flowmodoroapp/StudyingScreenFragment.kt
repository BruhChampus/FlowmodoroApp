package com.example.flowmodoroapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.databinding.FragmentStudyingScreenBinding


class StudyingScreenFragment : Fragment() {

    private lateinit var binding: FragmentStudyingScreenBinding
    private val args: StudyingScreenFragmentArgs by navArgs()
    private lateinit var viewModel:StudyingScreenFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStudyingScreenBinding.inflate(layoutInflater)

          viewModel = ViewModelProvider(this)[StudyingScreenFragmentViewModel::class.java]
        viewModel.startStudyTimer()

        viewModel.timeLiveData.observe(this) {
            binding.tvTimer.text = it
        }

        viewModel.studyingTimeLiveData.observe(this) {

            val studyTimePluralsValue = viewModel.studyingTimeLiveData.value?.let { studyTime ->
                this.resources.getQuantityString(
                    R.plurals.plulars_minutes,
                    studyTime, studyTime
                )
            }
             binding.tvTimeStudy.text = resources.getString(R.string.youre_studying).plus(studyTimePluralsValue)
        }

        binding.ivStop.setOnClickListener {
            it.findNavController().navigate(R.id.leaveDialog)
        }

        binding.ivBreak.setOnClickListener {
            if (viewModel.studyingTimeLiveData.value!! < 1) {
                Toast.makeText(
                    requireContext(),
                    "Atleast 1 minute needs to pass",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action =
                    StudyingScreenFragmentDirections.actionStudyingScreenFragmentToBreakScreenFragment(
                        binding.tvTaskName.text.toString(),
                        viewModel.studyingTimeLiveData.value ?: 1
                    )
                viewModel.stopStudyTimer()
                it.findNavController().navigate(action)
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.leaveDialog)
        }

        binding.tvTaskName.text = args.taskName


        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopStudyTimer()
    }



}