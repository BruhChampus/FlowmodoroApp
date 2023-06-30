package com.example.flowmodoroapp.fragments

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

import com.example.flowmodoroapp.viewmodels.StudyingScreenFragmentViewModel
import com.example.flowmodoroapp.databinding.FragmentStudyingScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class StudyingScreenFragment : Fragment() {

    private lateinit var binding: FragmentStudyingScreenBinding
    private val args: StudyingScreenFragmentArgs by navArgs()
    //private var viewModel: StudyingScreenFragmentViewModel? = null
    private val viewModel: StudyingScreenFragmentViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStudyingScreenBinding.inflate(layoutInflater)

//        val dao = FlowmodoroDB.getInstance(requireContext()).flowmodoroDao
//        val repository = SessionRepository(dao)
//        val factory = StudyingScreenFragmentViewModelFactory(repository)
     //   viewModel = ViewModelProvider(this, factory)[StudyingScreenFragmentViewModel::class.java]

        args.timeStudying.let {
            Log.i("Studying screen timeStudying", "${args.timeStudying}")
            // viewModel.setStudyingTimeMutableLiveData(it)
            viewModel!!.startStudyTimer(args.timeStudying)
        }
        args.taskName.let { binding.tvTaskName.text = it }

        //viewModel.startStudyTimer()

        viewModel!!.timeLiveData.observe(this) {
            binding.tvTimer.text = it
        }

        viewModel!!.studyingTimeLiveData.observe(this) {

            val studyTimePluralsValue = viewModel!!.studyingTimeLiveData.value?.let { studyTime ->
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
//TODO раскоментить
        binding.ivBreak.setOnClickListener {         //1
            if (viewModel!!.studyingTimeLiveDataLocal.value!! < 0) {
                Toast.makeText(
                    requireContext(),
                    "Atleast 1 minute needs to pass",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action =
                    StudyingScreenFragmentDirections.actionStudyingScreenFragmentToBreakScreenFragment(
                        binding.tvTaskName.text.toString(),
                        //viewModel.studyingTimeLiveData.value?:1
                        1
                    )
                viewModel!!.stopStudyTimer()
                it.findNavController().navigate(action)
            }
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.leaveDialog)
        }


        //Listener to check if ConfirmButton was pressed in leaveDialog
        setFragmentResultListener("isConfirmPressed") { _, bundle ->
            val result = bundle.getBoolean("isConfirmPressed")
            if(result){
               viewModel!!.stopStudyTimer()
                val session = Session(
                    date = viewModel.getCurrentDate(),
                    taskName = binding.tvTaskName.text.toString(),
                    minutes = binding.tvTimeStudy.text.toString()
                )
                lifecycleScope.launch(Dispatchers.IO) {viewModel.insertSession(session)}
                findNavController().navigate(R.id.mainScreenFragment)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        viewModel.stopStudyTimer()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("YourFragmentStuyd", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("YourFragmentStuyd", "onDestroy()")

    }


}