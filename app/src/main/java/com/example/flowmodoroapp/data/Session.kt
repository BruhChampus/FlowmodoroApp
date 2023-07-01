package com.example.flowmodoroapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val taskName: String,
    val minutes: String,
)