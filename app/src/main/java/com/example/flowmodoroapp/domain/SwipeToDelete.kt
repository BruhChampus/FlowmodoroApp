package com.example.flowmodoroapp.domain

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flowmodoroapp.SessionsRecyclerViewAdapter
import com.example.flowmodoroapp.data.FlowmodoroDAO
import com.example.flowmodoroapp.data.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SwipeToDelete() {
    fun createSimpleItemTouchHelper(
        coroutineScope: CoroutineScope,
        flowmodoroDao: FlowmodoroDAO,
        sessionsList: ArrayList<Session>,
        adapter: SessionsRecyclerViewAdapter
    ): ItemTouchHelper.SimpleCallback {
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Remove swiped item from list and notify the RecyclerView
                val position = viewHolder.adapterPosition
                coroutineScope.launch { flowmodoroDao.deleteSession(sessionsList[position]) }
                sessionsList.removeAt(position)
                adapter.notifyItemRemoved(position)
             }
        }
        return simpleItemTouchCallback
    }
}