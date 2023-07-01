package com.example.flowmodoroapp.domain

import android.view.View
import android.widget.Toast
import com.example.flowmodoroapp.SessionsRecyclerViewAdapter
import com.example.flowmodoroapp.data.FlowmodoroDAO
import com.example.flowmodoroapp.data.Session
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SnackbarAfterItemDeletion {
    fun createSnackbar(
        view: View,
        coroutineScope: CoroutineScope,
    ) {

        //Creating snackbar
        val snackbar = Snackbar.make(
            view,
            "Item deleted",
            Snackbar.LENGTH_SHORT
        )
//        snackbar.setAction("Cancel") {
//            // Restore deleted item
//            deletedSession.let {
//                sessionsList.add(position, it)
//                adapter.notifyItemInserted(position)
//            }
//        }

        snackbar.show()




        coroutineScope.launch {
            delay(2000)
            snackbar.dismiss()
        }
    }
}