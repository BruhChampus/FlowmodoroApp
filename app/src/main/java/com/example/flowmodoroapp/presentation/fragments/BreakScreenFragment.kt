package com.example.flowmodoroapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.presentation.viewmodels.BreakScreenFragmentViewModel
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.presentation.viewmodels.SharedBreakScreenNLeaveDialogViewModel
import com.example.flowmodoroapp.databinding.FragmentBreakScreenBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreakScreenFragment : Fragment() {

    private lateinit var binding: FragmentBreakScreenBinding
    private val args: BreakScreenFragmentArgs by navArgs()
    private val viewModel: BreakScreenFragmentViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBreakScreenBinding.inflate(layoutInflater)
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

            //set text to the timer
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
                //If confirmButton pressed then stop timer, create session object, insert it into db and navigate to main screen
                viewModel.stopBreakTimer()
                val session = Session(
                    date = viewModel.getCurrentDate(),
                    taskName = binding.tvTaskName.text.toString(),
                    minutes = this.resources.getQuantityString(
                        R.plurals.plulars_minutes,
                        timeStudying,
                        timeStudying
                    )
                )
                lifecycleScope.launch(Dispatchers.IO) { viewModel.insertSession(session) }
                findNavController().navigate(R.id.mainScreenFragment)
            }
        }
        return binding.root
    }

}
