package com.example.flowmodoroapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.presentation.viewmodels.SharedBreakScreenNLeaveDialogViewModel
import com.example.flowmodoroapp.databinding.FragmentLeaveDialogBinding


class LeaveDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentLeaveDialogBinding
    private lateinit var sharedViewModel: SharedBreakScreenNLeaveDialogViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLeaveDialogBinding.inflate(layoutInflater)

        sharedViewModel =
            ViewModelProvider(requireActivity())[SharedBreakScreenNLeaveDialogViewModel::class.java]
        sharedViewModel.openDialog()

        binding.ivConfirm.setOnClickListener {
            sharedViewModel.closeDialog()
            //pass to the fragment that the confirm button was pressed
            setFragmentResult("isConfirmPressed", bundleOf("isConfirmPressed" to true))
             dismiss()
        }
        binding.ivDismiss.setOnClickListener {
            sharedViewModel.closeDialog()
            setFragmentResult("isConfirmPressed", bundleOf("isConfirmPressed" to false))
            dismiss()
        }

        // Inflate the layout for this fragment
        return binding.root
    }


}