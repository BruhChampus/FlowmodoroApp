package com.example.flowmodoroapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowmodoroapp.StudyTimer
import com.example.flowmodoroapp.data.SessionRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class StudyingScreenFragmentViewModel(private val repository: SessionRepository) : ViewModel() {

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



    //TODO Все таки попробовать в onDestroyView останавливать таймер только придумать как сохранять состояние
    //TODO сделать что свайпом можно удалять елементы из бд
    private var studyTimer: StudyTimer = StudyTimer(
        timeMutableLiveData = timeMutableLiveData,
        studyingTimeMutableLiveData = studyingTimeMutableLiveData,
        studyingTimeMutableLiveDataLocal = studyingTimeMutableLiveDataLocal,
        coroutineScope = CoroutineScope(Dispatchers.Main)
    )

    fun startStudyTimer(minutesStudying: Int) {
        studyTimer.startStudyTimer(minutesStudying)
    }


    fun stopStudyTimer() {
        studyTimer.stopStudyTimer()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("OnCleared", "ViewMOdel is cleared")
    }


}