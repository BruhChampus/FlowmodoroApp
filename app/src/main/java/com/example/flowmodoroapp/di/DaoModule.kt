package com.example.flowmodoroapp.di

import com.example.flowmodoroapp.data.FlowmodoroDAO
import com.example.flowmodoroapp.data.FlowmodoroDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val daoModule = module {
    single<FlowmodoroDAO>{
        FlowmodoroDB.getInstance(context = androidContext()).flowmodoroDao
    }
}