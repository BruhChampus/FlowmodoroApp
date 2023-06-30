package com.example.flowmodoroapp

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class StudyTimer(
    val timeMutableLiveData: MutableLiveData<String>,
    val studyingTimeMutableLiveData: MutableLiveData<Int>,
    val studyingTimeMutableLiveDataLocal: MutableLiveData<Int>,
    val coroutineScope: CoroutineScope
) {
    private var timer: Timer? = null

    fun startStudyTimer(minutesStudying: Int) {
        if (timer == null) {
            var minutesStudyingOverAll: Int = minutesStudying
            studyingTimeMutableLiveData.value = minutesStudyingOverAll

            var minutesMustPass = 1

            timer = Timer()
            val startTime = System.currentTimeMillis()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = (currentTime - startTime)
                    coroutineScope.launch {
                        timeMutableLiveData.value = updateTimerText(elapsedTime)
                    }

                    val minutesPassed = elapsedTime / (1000 * 60)

                    if (minutesPassed >= minutesMustPass) {
                        coroutineScope.launch {
                            studyingTimeMutableLiveDataLocal.value = minutesPassed.toInt()
                        }

                        minutesMustPass++
                        minutesStudyingOverAll++

                        coroutineScope.launch {
                            studyingTimeMutableLiveData.value = minutesStudyingOverAll
                        }
                    }
                }
            }, 0, 1000)
        }
    }


    fun stopStudyTimer() {
        timer?.cancel()
        timer = null
    }

    //Formating time value to normal view
    private fun updateTimerText(millisUntilFinished: Long): String {
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

}