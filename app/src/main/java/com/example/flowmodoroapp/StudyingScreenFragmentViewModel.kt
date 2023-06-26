package com.example.flowmodoroapp

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

    /**Stores a formatted time value (digits that is inside timer)*/
    private var timeMutableLiveData = MutableLiveData<String>()
    val timeLiveData: LiveData<String>
        get() = timeMutableLiveData


            /**Stores  total time user is studying*/
    private var studyingTimeMutableLiveData = MutableLiveData<Int>()
    val studyingTimeLiveData: LiveData<Int>
        get() = studyingTimeMutableLiveData



    /**
     * this variable is needed so that after returning from a break, the user cannot press the break button again.
     * If you use studyingTimeMutableLiveData then the user will be able to constantly press the break,
     * because the value of how much TOTAL time the user is studying are stored in studyingTimeMutableLiveData*/
    private var studyingTimeMutableLiveDataLocal = MutableLiveData<Int>(0)
    val studyingTimeLiveDataLocal: LiveData<Int>
        get() = studyingTimeMutableLiveDataLocal


    private var timer: Timer? = null

//TODO сделать нотификации со звуком, которые будут приходить когда закончился перерыв
//TODO сделать нотификацию которая будет показівать сколько времени прошло

     //TODO onDestroyView не подошел, найти другой метод останавливать таймеры
    //TODO сделать что свайпом можно удалять елементы из бд
    fun startStudyTimer(a: Int) {
        if (timer == null) {
            var minutesStudying: Int = a
            studyingTimeMutableLiveData.value = minutesStudying

            var minutesMustPass = 1

            timer = Timer()
            val startTime = System.currentTimeMillis()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = (currentTime - startTime)
                    CoroutineScope(Dispatchers.Main).launch {
                        timeMutableLiveData.value = updateTimerText(elapsedTime)
                    }

                    val minutesPassed = elapsedTime / (1000 * 60)

                    if (minutesPassed >= minutesMustPass) {
                        CoroutineScope(Dispatchers.Main).launch { studyingTimeMutableLiveDataLocal.value = minutesPassed.toInt() }

                        minutesMustPass++
                        minutesStudying++

                        CoroutineScope(Dispatchers.Main).launch {
                            studyingTimeMutableLiveData.value = minutesStudying
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

    override fun onCleared() {
        super.onCleared()
        Log.i("OnCleared", "ViewMOdel is cleared")
    }


    //Formating time value to normal view
    private fun updateTimerText(millisUntilFinished: Long): String {
        val minutes = (millisUntilFinished / 1000) / 60
        val seconds = (millisUntilFinished / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


}