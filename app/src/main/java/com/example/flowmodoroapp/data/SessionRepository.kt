package com.example.flowmodoroapp.data

import kotlinx.coroutines.flow.Flow

class SessionRepository(private val dao: FlowmodoroDAO) {

    suspend fun insertSession(session: Session)  {
        dao.insertSession(session)
    }

    suspend fun deleteSession(session: Session) {
        return dao.deleteSession(session)
    }

    fun getSessions(): Flow<List<Session>> {
        return dao.getAllSessions()
    }
}