package com.example.flowmodoroapp.presentation.di

import com.example.flowmodoroapp.presentation.viewmodels.BreakScreenFragmentViewModel
import com.example.flowmodoroapp.presentation.viewmodels.StudyingScreenFragmentViewModel
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