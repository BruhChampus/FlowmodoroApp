package com.example.flowmodoroapp.domain

import java.text.SimpleDateFormat
import java.util.*

class CurrentDateGetter {
    //Get formatted current date
    fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return format.format(currentDate)
    }
}