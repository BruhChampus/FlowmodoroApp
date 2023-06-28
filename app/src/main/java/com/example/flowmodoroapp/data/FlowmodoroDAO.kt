package com.example.flowmodoroapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowmodoroDAO {
    @Insert
    suspend fun insertSession(session: Session):Flow<Session>

    @Delete
    suspend fun deleteSession(session: Session)


    @Query("SELECT *FROM session_table")
    fun getAllSessions():Flow<List<Session>>
}