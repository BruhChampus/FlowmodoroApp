package com.example.flowmodoroapp

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.databinding.FragmentBreakScreenBinding
import kotlinx.coroutines.delay


class BreakScreenFragment : Fragment() {

    private lateinit var binding: FragmentBreakScreenBinding
    private val args: BreakScreenFragmentArgs by navArgs()

    private lateinit var viewModel: BreakScreenFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBreakScreenBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.ivStop.setOnClickListener {
            it.findNavController().navigate(R.id.leaveDialog)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            findNavController().navigate(R.id.leaveDialog)
        }
        viewModel = ViewModelProvider(this)[BreakScreenFragmentViewModel::class.java]

        val timeStudying = args.timeStudying
        Log.i("BreakScreenFragment timeStudying", " ${args.timeStudying}")
        val taskName = args.taskName
        viewModel.timeLiveData.observe(this) {
            binding.tvTimer.text = it
        }
        viewModel.startBreakTimer(timeStudying)

        viewModel.isTimerOnLiveData.observe(this) {
            if (it == false) {
                val sharedViewModel =
                    ViewModelProvider(requireActivity())[SharedBreakScreenNLeaveDialogViewModel::class.java]

                val action =
                    BreakScreenFragmentDirections.actionBreakScreenFragmentToStudyingScreenFragment(
                        taskName,
                        timeStudying
                    )
                sharedViewModel.showDialogIsOpenLiveData.observe(
                    this
                ) {
                    //In our case dismisses dialog if it is open
                    if (sharedViewModel.showDialogIsOpenLiveData.value == true) {
                        findNavController().navigateUp()
                    }
                }
                viewModel.stopBreakTimer()
                findNavController().navigate(action)
             }
        }

        binding.tvTaskName.text = taskName
        binding.tvTimeStudy.text = resources.getString(R.string.time_studying).plus(
            this.resources.getQuantityString(
                R.plurals.plulars_minutes,
                timeStudying,
                timeStudying
            )
        )
        return binding.root
    }


}
