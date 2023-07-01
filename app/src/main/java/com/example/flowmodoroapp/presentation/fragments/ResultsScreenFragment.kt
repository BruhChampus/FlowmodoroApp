package com.example.flowmodoroapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowmodoroapp.R
import com.example.flowmodoroapp.presentation.SessionsRecyclerViewAdapter
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import com.example.flowmodoroapp.databinding.FragmentResultsScreenBinding
import com.example.flowmodoroapp.domain.SwipeToDelete
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ResultsScreenFragment : Fragment() {

    private lateinit var binding: FragmentResultsScreenBinding
     private val repository: SessionRepository by inject()

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
            repository.getAllSessions().collect {
               val sessionsList = it as ArrayList<Session>
                initRecyclerView(sessionsList)
            }
         }
        return binding.root
    }

    private fun initRecyclerView(sessionsList:ArrayList<Session>) {
        binding.rvSessionsList.layoutManager = LinearLayoutManager(requireContext())
        if(sessionsList.isEmpty()){
            binding.rvSessionsList.visibility = View.GONE
            binding.tvNoDataYet.visibility = View.VISIBLE
        }else{
            binding.rvSessionsList.visibility = View.VISIBLE
            binding.tvNoDataYet.visibility = View.GONE
        }

        val adapter = SessionsRecyclerViewAdapter(sessionsList)
        binding.rvSessionsList.adapter = adapter

        val simpleItemTouchHelper = SwipeToDelete().createSimpleItemTouchHelper(
            lifecycleScope,
            repository,
            sessionsList,
            adapter,
            binding.rvSessionsList
        )

        ItemTouchHelper(
            simpleItemTouchHelper
        ).attachToRecyclerView(binding.rvSessionsList)
    }
}