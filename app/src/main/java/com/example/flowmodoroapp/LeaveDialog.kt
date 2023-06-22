package com.example.flowmodoroapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.flowmodoroapp.databinding.FragmentLeaveDialogBinding


class LeaveDialog : DialogFragment() {

    private lateinit var binding: FragmentLeaveDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLeaveDialogBinding.inflate(layoutInflater)


        val sharedViewModel = ViewModelProvider(requireActivity())[SharedBreakScreenNLeaveDialogViewModel::class.java]
        sharedViewModel.openDialog()

        binding.ivConfirm.setOnClickListener {
             findNavController().navigate(R.id.mainScreenFragment)
            sharedViewModel.closeDialog()
            dismiss()
        }
        binding.ivDismiss.setOnClickListener {
            sharedViewModel.closeDialog()
            dismiss()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        dismiss()

    }

    override fun onStop() {
        super.onStop()
        dismiss()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    companion object {
        private const val TAG = "YourFragment"
    }


}