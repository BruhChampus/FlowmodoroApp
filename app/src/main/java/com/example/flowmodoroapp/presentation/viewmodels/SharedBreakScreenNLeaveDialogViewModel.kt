package com.example.flowmodoroapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



/**This viewModel is needed in order to communicate with the dialogue.
 * Needed to prevent the application from crashing when a dialog is open and a transition to another fragment occurs */
class SharedBreakScreenNLeaveDialogViewModel:ViewModel() {
    private var showDialogIsOpenMutableLiveData = MutableLiveData<Boolean>(false)
    val showDialogIsOpenLiveData: LiveData<Boolean>
        get() = showDialogIsOpenMutableLiveData


    fun closeDialog(){
        showDialogIsOpenMutableLiveData.value = false
    }

    fun openDialog(){
        showDialogIsOpenMutableLiveData.value = true
    }
}