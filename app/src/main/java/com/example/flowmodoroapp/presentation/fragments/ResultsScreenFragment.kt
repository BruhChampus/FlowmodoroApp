package com.example.flowmodoroapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.SessionsRecyclerViewAdapter
import com.example.flowmodoroapp.data.FlowmodoroDAO
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import com.example.flowmodoroapp.databinding.FragmentResultsScreenBinding
import com.example.flowmodoroapp.domain.SwipeToDelete
import com.example.flowmodoroapp.viewmodels.ResultsFragmentViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ResultsScreenFragment : Fragment() {

    private lateinit var binding: FragmentResultsScreenBinding
    private val flowmodoroDAO: FlowmodoroDAO by inject()
     private val viewModel: ResultsFragmentViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentResultsScreenBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.ivClose.setOnClickListener {
            it.findNavController().navigate(R.id.mainScreenFragment)
        }
        binding.ivSettings.setOnClickListener {
            it.findNavController().navigate(R.id.settingsFragment)
        }
        lifecycleScope.launch {
            flowmodoroDAO.getAllSessions().collect {
               val sessionsList = it as ArrayList<Session>
                initRecyclerView(sessionsList)
            }
         }

        return binding.root
    }
//Короче доделаешь

    fun initRecyclerView(sessionsList:ArrayList<Session>) {
        binding.rvSessionsList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SessionsRecyclerViewAdapter(sessionsList)
        binding.rvSessionsList.adapter = adapter
        if(sessionsList.isEmpty()){
            binding.rvSessionsList.visibility = View.GONE
            binding.tvNoDataYet.visibility = View.VISIBLE
        }else{
            binding.rvSessionsList.visibility = View.VISIBLE
            binding.tvNoDataYet.visibility = View.GONE
        }

        val simpleItemTouchHelper = SwipeToDelete().createSimpleItemTouchHelper(
            lifecycleScope,
            flowmodoroDAO,
            sessionsList,
            adapter
        )

        ItemTouchHelper(
            simpleItemTouchHelper
        ).attachToRecyclerView(binding.rvSessionsList)
        //  displaySessions()
    }

//    private fun displaySessions() {
//        viewModel.session.observe(requireActivity()) {
//            adapter?.setList(it)
//            adapter?.notifyDataSetChanged()
//        }
//    }


}