package com.example.flowmodoroapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flowmodoroapp.domain.StudyTimer
import com.example.flowmodoroapp.data.Session
import com.example.flowmodoroapp.data.SessionRepository
import com.example.flowmodoroapp.domain.CurrentDateGetter

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


    //Get formatted current date
    fun getCurrentDate(): String {
        return CurrentDateGetter().getCurrentDate()
    }

    suspend fun insertSession(session: Session){
        repository.insertSession(session)
    }


}