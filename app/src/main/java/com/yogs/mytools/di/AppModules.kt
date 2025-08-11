package com.yogs.mytools.di

import com.yogs.mytools.data.repository.ResolutionChangerRepository
import com.yogs.mytools.viewmodel.ResolutionChangerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ResolutionChangerRepository(androidContext()) }
    viewModel { ResolutionChangerViewModel(get()) }
}