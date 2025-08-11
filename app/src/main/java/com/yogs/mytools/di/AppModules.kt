package com.yogs.mytools.di

import com.yogs.mytools.data.DataRepository
import com.yogs.mytools.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        DataRepository(androidContext())
    }

    viewModel{
        HomeViewModel(get())
    }
}