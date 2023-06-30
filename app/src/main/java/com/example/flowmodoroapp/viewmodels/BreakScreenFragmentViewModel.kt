package com.example.flowmodoroapp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowmodoroapp.BreakTimer
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class BreakScreenFragmentViewModel(private val repository: SessionRepository) : ViewModel() {
    val session = repository.getSessions()
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


    fun getCurrentDate(): String {
        val year = Calendar.YEAR
        val month = Calendar.WEEK_OF_MONTH
        val day = Calendar.DAY_OF_MONTH
        return String.format("DD.MM.YYYY", day, month, year)
    }

    suspend fun insertSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertSession(session) }
    }


}