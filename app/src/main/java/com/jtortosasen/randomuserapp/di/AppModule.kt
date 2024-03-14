package com.jtortosasen.randomuserapp.di

import com.jtortosasen.randomuserapp.ui.viewmodels.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { OverviewViewModel (get()) }
}