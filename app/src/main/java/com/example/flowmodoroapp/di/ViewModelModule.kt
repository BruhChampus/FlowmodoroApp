package com.example.flowmodoroapp.di

import com.example.flowmodoroapp.viewmodels.BreakScreenFragmentViewModel
import com.example.flowmodoroapp.viewmodels.StudyingScreenFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        StudyingScreenFragmentViewModel(
            repository = get()
        )
    }

    viewModel {
        BreakScreenFragmentViewModel(
            repository = get()
        )
    }
}