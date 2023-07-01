package com.example.flowmodoroapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.data.Session

import com.example.flowmodoroapp.presentation.viewmodels.StudyingScreenFragmentViewModel
import com.example.flowmodoroapp.databinding.FragmentStudyingScreenBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class StudyingScreenFragment : Fragment() {

    private lateinit var binding: FragmentStudyingScreenBinding
    private val args: StudyingScreenFragmentArgs by navArgs()
    private val viewModel: StudyingScreenFragmentViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStudyingScreenBinding.inflate(layoutInflater)
        args.timeStudying.let {
            //Start study timer passing args.timeStudying parameter to it
            viewModel.startStudyTimer(args.timeStudying)
        }
        args.taskName.let { binding.tvTaskName.text = it }

        viewModel.timeLiveData.observe(this) {
            binding.tvTimer.text = it
        }

        //Observing studyingTime and changing text inside timer
        viewModel.studyingTimeLiveData.observe(this) {
            val studyTimePluralsValue = viewModel.studyingTimeLiveData.value?.let { studyTime ->
                this.resources.getQuantityString(
                    R.plurals.plulars_minutes,
                    studyTime, studyTime
                )
            }
            binding.tvTimeStudy.text =
                resources.getString(R.string.youre_studying).plus(studyTimePluralsValue)
        }

        binding.ivStop.setOnClickListener {
            it.findNavController().navigate(R.id.leaveDialog)
        }
        /**
         * check if the user has been studying for more than a minute, if yes - transfer to a break
         * */
        binding.ivBreak.setOnClickListener {
            if (viewModel.studyingTimeLiveDataLocal.value!! < 1) {
                Toast.makeText(
                    requireContext(),
                    "Atleast 1 minute needs to pass",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //passing arguments for break fragment
                val action =
                    StudyingScreenFragmentDirections.actionStudyingScreenFragmentToBreakScreenFragment(
                        binding.tvTaskName.text.toString(),
                        viewModel.studyingTimeLiveData.value?:1
                    )
                viewModel!!.stopStudyTimer()
                it.findNavController().navigate(action)
            }
        }


        //when back button on phone pressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.leaveDialog)
        }


        //Listener to check if ConfirmButton was pressed in leaveDialog
        setFragmentResultListener("isConfirmPressed") { _, bundle ->
            val result = bundle.getBoolean("isConfirmPressed")
            val studyTimePluralsValue = viewModel.studyingTimeLiveData.value?.let { studyTime ->
                this.resources.getQuantityString(
                    R.plurals.plulars_minutes,
                    studyTime, studyTime
                )
            }
            //If ConfirmButton was pressed in leaveDialog, stop studyTime, create Session object
            // and insert into database, navigate to mainScreen
            if(result){
               viewModel.stopStudyTimer()
                val session = Session(
                    date = viewModel.getCurrentDate(),
                    taskName = binding.tvTaskName.text.toString(),
                    minutes = studyTimePluralsValue?:"error"
                )
                lifecycleScope.launch(Dispatchers.IO) {viewModel.insertSession(session)}
                findNavController().navigate(R.id.mainScreenFragment)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }




}