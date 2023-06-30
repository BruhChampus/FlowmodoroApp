package com.example.flowmodoroapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.viewmodels.BreakScreenFragmentViewModel
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.viewmodels.SharedBreakScreenNLeaveDialogViewModel
import com.example.flowmodoroapp.databinding.FragmentBreakScreenBinding


class BreakScreenFragment : Fragment() {

    private lateinit var binding: FragmentBreakScreenBinding
    private val args: BreakScreenFragmentArgs by navArgs()

    private var viewModel: BreakScreenFragmentViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBreakScreenBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[BreakScreenFragmentViewModel::class.java]
        val timeStudying = args.timeStudying
        val taskName = args.taskName
        binding.tvTaskName.text = taskName
        binding.tvTimeStudy.text = resources.getString(R.string.time_studying).plus(
            this.resources.getQuantityString(
                R.plurals.plulars_minutes,
                timeStudying,
                timeStudying
            )
        )

        // Inflate the layout for this fragment
        binding.ivStop.setOnClickListener {
            it.findNavController().navigate(R.id.leaveDialog)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.leaveDialog)
        }

        viewModel!!.timeLiveData.observe(this) {
            binding.tvTimer.text = it
        }

        viewModel!!.isTimerOnLiveData.observe(this) {
            /**
             * If time expired than check if dialog is open and navigate*/
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
                viewModel!!.stopBreakTimer()
                findNavController().navigate(action)
            }
        }

        viewModel!!.startBreakTimer(timeStudying, requireContext())

        //Listener to check if ConfirmButton was pressed in leaveDialog
        setFragmentResultListener("isConfirmPressed") { _, bundle ->
            val result = bundle.getBoolean("isConfirmPressed")
            if (result) {
                viewModel!!.stopBreakTimer()
                //INSERT INTO DB
                viewModel = null
                findNavController().navigate(R.id.mainScreenFragment)
            }
        }
        val session = Session(
            date = viewModel!!.getCurrentDate(),
            taskName = binding.tvTaskName.text.toString(),
            minutes = binding.tvTimeStudy.text.toString()
        )
        return binding.root
    }


}
