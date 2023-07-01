package com.example.flowmodoroapp.domain

import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.flowmodoroapp.presentation.SessionsRecyclerViewAdapter
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SwipeToDelete() {

    fun createSimpleItemTouchHelper(
        coroutineScope: CoroutineScope,
        repository: SessionRepository,
        sessionsList: ArrayList<Session>,
        adapter: SessionsRecyclerViewAdapter,
        view: View,
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

                coroutineScope.launch {
                    repository.deleteSession(sessionsList[position])
                }
                sessionsList.removeAt(position)
                adapter.notifyItemRemoved(position)
                SnackbarAfterItemDeletion().createSnackbar(
                    view,
                    coroutineScope
                )
            }
        }
        return simpleItemTouchCallback
    }


}