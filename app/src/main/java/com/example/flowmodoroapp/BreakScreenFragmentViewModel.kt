package com.example.flowmodoroapp

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BreakScreenFragmentViewModel : ViewModel() {
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



}