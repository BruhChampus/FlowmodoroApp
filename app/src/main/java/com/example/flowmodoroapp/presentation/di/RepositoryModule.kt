package com.example.flowmodoroapp.presentation.di

import com.example.flowmodoroapp.data.SessionRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<SessionRepository>{
        SessionRepository(dao = get())
    }
}