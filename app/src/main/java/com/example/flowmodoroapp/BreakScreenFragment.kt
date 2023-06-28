package com.example.flowmodoroapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.databinding.FragmentBreakScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BreakScreenFragment : Fragment() {

    private lateinit var binding: FragmentBreakScreenBinding
    private val args: BreakScreenFragmentArgs by navArgs()

    private var viewModel: BreakScreenFragmentViewModel? = null
    private lateinit var breakEndNotification: BreakEndNotification


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

        return binding.root
    }


}
