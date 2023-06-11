package com.example.flowmodoroapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.flowmodoroapp.databinding.FragmentMainScreenBinding
import com.example.flowmodoroapp.databinding.FragmentStudyingScreenBinding
import com.example.flowmodoroapp.databinding.LeaveDialogBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudyingScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudyingScreenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentStudyingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStudyingScreenBinding.inflate(layoutInflater)
        binding.ivStop.setOnClickListener {
            val dialogBinding = LeaveDialogBinding.inflate(layoutInflater)
            val dialog =
                AlertDialog.Builder(requireContext()).setView(dialogBinding.clLeaveDialog).create()
            dialogBinding.ivConfirm.setOnClickListener {
                it.findNavController().navigate(R.id.mainScreenFragment)
                //TODO fix bug, when clicking on confirmBTN app is crashing
            }
            dialogBinding.ivDismiss.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


        binding.ivBreak.setOnClickListener {
            it.findNavController().navigate(R.id.breakScreenFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudyingScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudyingScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}