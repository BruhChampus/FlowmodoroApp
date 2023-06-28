package com.example.flowmodoroapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.viewmodels.SharedBreakScreenNLeaveDialogViewModel
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
            findNavController().navigate(R.id.mainScreenFragment)
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
        sharedViewModel.closeDialog()
        dismiss()
    }

    override fun onStop() {
        super.onStop()
        sharedViewModel.closeDialog()
        dismiss()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.closeDialog()
        dismiss()
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.closeDialog()
        dismiss()
        Log.d(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        sharedViewModel.closeDialog()
        dismiss()
        Log.d(TAG, "onDetach")
    }

    companion object {
        private const val TAG = "YourFragmentLeaveDialog"
    }


}