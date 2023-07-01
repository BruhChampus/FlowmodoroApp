package com.example.flowmodoroapp.domain

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData

class BreakTimer(
    val timeMutableLiveData: MutableLiveData<String>,
    val isTimerOn: MutableLiveData<Boolean>) {
        private var countDownTimer: CountDownTimer? = null

        fun startBreakTimer(minutes: Int, context: Context) {
            isTimerOn.value = true
            if (countDownTimer == null) {
                val millisInFuture: Long = ((minutes.toLong() * 60_000) / 5)
                Log.i("Break", "$millisInFuture")
                countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timeMutableLiveData.value = updateTimerText(millisUntilFinished)
                        Log.i("BreakTimer", "ticking")

                    }

                    override fun onFinish() {
                        isTimerOn.value = false
                        stopBreakTimer()
                        val breakEndNotification = BreakEndNotification(context)
                        breakEndNotification.createNotificationChannel()
                        breakEndNotification.displayNotification()
                    }
                }
                (countDownTimer as CountDownTimer).start()
                Log.i("BreakTimer", "started")
            }
            Log.i("BreakTimer", "not null")

        }


        fun stopBreakTimer() {
            countDownTimer?.cancel()
            countDownTimer = null
        }

        private fun updateTimerText(millisUntilFinished: Long): String {
            val minutes = (millisUntilFinished / 1000) / 60
            val seconds = (millisUntilFinished / 1000) % 60
            return String.format("%02d:%02d", minutes, seconds)
        }
    }