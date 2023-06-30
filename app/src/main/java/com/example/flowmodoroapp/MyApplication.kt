package com.example.flowmodoroapp

import android.app.Application
import com.example.flowmodoroapp.di.daoModule
import com.example.flowmodoroapp.di.repositoryModule
import com.example.flowmodoroapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApplication)
            modules(listOf(daoModule, repositoryModule, viewModelModule))
        }
    }
}