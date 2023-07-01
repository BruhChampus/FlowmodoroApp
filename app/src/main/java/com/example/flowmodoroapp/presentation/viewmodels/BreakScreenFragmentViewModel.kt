package com.example.flowmodoroapp.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowmodoroapp.domain.BreakTimer
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import com.example.flowmodoroapp.domain.CurrentDateGetter


class BreakScreenFragmentViewModel( private val repository: SessionRepository) : ViewModel() {

    private var timeMutableLiveData = MutableLiveData<String>()
    val timeLiveData: LiveData<String>
        get() = timeMutableLiveData


    private var isTimerOn = MutableLiveData<Boolean>()
    val isTimerOnLiveData: LiveData<Boolean>
        get() = isTimerOn


    private val breakTimer = BreakTimer(
        timeMutableLiveData = timeMutableLiveData,
        isTimerOn = isTimerOn
    )

    fun startBreakTimer(minutes: Int, context: Context) {
        breakTimer.startBreakTimer(minutes, context)

    }

    fun stopBreakTimer() {
        breakTimer.stopBreakTimer()
    }


    //Get formatted current date
    fun getCurrentDate(): String {
      return CurrentDateGetter().getCurrentDate()
    }

   suspend fun insertSession(session: Session) {
    repository.insertSession(session)
   }


}