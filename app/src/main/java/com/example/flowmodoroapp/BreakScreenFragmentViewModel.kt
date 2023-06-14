package com.example.flowmodoroapp

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BreakScreenFragmentViewModel : ViewModel() {
    private var timeMutableLiveData = MutableLiveData<String>()
    val timeLiveData: LiveData<String>
        get() = timeMutableLiveData


    private var countDownTimer: CountDownTimer? = null

    fun startBreakTimer(minutes: Int) {
        if (countDownTimer == null) {
            val millisInFuture:Long = ((minutes.toLong()*60_000)/5)
            Log.i("Break", "$millisInFuture")
            countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                   timeMutableLiveData.value = updateTimerText(millisUntilFinished)
                }

                override fun onFinish() {
                }
            }
            Log.i("Break", "started????")
            (countDownTimer as CountDownTimer).start()
        }
        Log.i("Break", "not null")

    }


    fun stopBreakTimer() {
        countDownTimer?.onFinish()
        countDownTimer = null
    }

    private fun updateTimerText(millisUntilFinished: Long): String {
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


}