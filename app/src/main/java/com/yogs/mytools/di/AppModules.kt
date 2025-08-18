package com.yogs.mytools.di

import com.yogs.mytools.data.preferences.DataPreference
import com.yogs.mytools.data.preferences.datastore
import com.yogs.mytools.data.repository.ResolutionChangerRepository
import com.yogs.mytools.data.repository.RouterManagerRepository
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import com.yogs.mytools.viewmodel.RouterManagerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DataPreference(androidContext().datastore) }

    single { ResolutionChangerRepository(androidContext(), get()) }
    single { RouterManagerRepository(androidContext()) }

    viewModel { ResolutionChangerViewModel(get()) }
    viewModel { RouterManagerViewModel(get()) }
}