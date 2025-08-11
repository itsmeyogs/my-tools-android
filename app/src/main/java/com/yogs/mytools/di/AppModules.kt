package com.yogs.mytools.di

import com.yogs.mytools.data.repository.DataRepository
import com.yogs.mytools.data.repository.ResolutionChangerRepository
import com.yogs.mytools.viewmodel.HomeViewModel
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DataRepository(androidContext()) }
    single { ResolutionChangerRepository(androidContext()) }


    viewModel{ HomeViewModel(get()) }
    viewModel { ResolutionChangerViewModel(get()) }
}