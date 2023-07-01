package com.example.flowmodoroapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Session::class], version = 1)
abstract class FlowmodoroDB : RoomDatabase() {
    abstract val flowmodoroDao: FlowmodoroDAO
    companion object {
        @Volatile
        private var INSTANCE: FlowmodoroDB? = null
        fun getInstance(context: Context): FlowmodoroDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlowmodoroDB::class.java,
                        "flowmodoro_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}