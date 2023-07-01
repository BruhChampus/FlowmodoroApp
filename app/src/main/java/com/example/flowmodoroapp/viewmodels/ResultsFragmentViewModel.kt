package com.example.flowmodoroapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.flowmodoroapp.data.SessionRepository

class ResultsFragmentViewModel(repository: SessionRepository):ViewModel() {
    val session = repository.getSessions()//is needed for observing changes in DB


}