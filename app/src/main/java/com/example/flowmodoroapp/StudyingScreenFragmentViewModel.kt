package com.example.flowmodoroapp

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.Timer
import java.util.TimerTask


class StudyingScreenFragmentViewModel : ViewModel() {
    private var timeMutableLiveData = MutableLiveData<String>()
    val timeLiveData: LiveData<String>
        get() = timeMutableLiveData

    private var studyingTimeMutableLiveData = MutableLiveData<Int>(0)
    val studyingTimeLiveData: LiveData<Int>
        get() = studyingTimeMutableLiveData


    private var timer: Timer? = null
    private var elapsedTime: Long = 0
    private var minutesStudying:Int = 0
    private var minutesOnDivide = 1

    fun startStudyTimer() {
        if (timer == null) {
            timer = Timer()
            val startTime = System.currentTimeMillis()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val currentTime = System.currentTimeMillis()
                    elapsedTime = (currentTime - startTime)
                     CoroutineScope(Dispatchers.Main).launch {
                        timeMutableLiveData.value = updateTimerText(elapsedTime)
                    }

                    val minutesPassed = elapsedTime / (1000 * 60)
                    if (minutesPassed >= minutesOnDivide) {
                        minutesOnDivide++
                        minutesStudying++
                        CoroutineScope(Dispatchers.Main).launch {
                            studyingTimeMutableLiveData.value = minutesStudying
                        }
                     }
                    Log.i("StudyingTimer", "ticking")

                }
            }, 0, 1000)
        }
    }

    fun stopStudyTimer() {
        timer?.cancel()
        timer = null
    }

    override fun onCleared() {
        super.onCleared()
    }

    private fun updateTimerText(millisUntilFinished: Long): String {
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


}