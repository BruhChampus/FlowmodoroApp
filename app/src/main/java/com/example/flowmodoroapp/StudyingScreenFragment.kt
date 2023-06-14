package com.example.flowmodoroapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flowmodoroapp.databinding.FragmentMainScreenBinding
import com.example.flowmodoroapp.databinding.FragmentStudyingScreenBinding


class StudyingScreenFragment : Fragment() {

    private lateinit var binding: FragmentStudyingScreenBinding
    val args: StudyingScreenFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStudyingScreenBinding.inflate(layoutInflater)

        binding.ivStop.setOnClickListener {
            it.findNavController().navigate(R.id.leaveDialog)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.leaveDialog)

        }
        binding.ivBreak.setOnClickListener {
            it.findNavController().navigate(R.id.breakScreenFragment)
        }


        binding.tvTaskName.text = args.taskName

        // Inflate the layout for this fragment
        return binding.root
    }


}