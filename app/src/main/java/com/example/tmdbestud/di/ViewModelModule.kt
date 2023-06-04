package com.example.tmdbestud.di

import com.example.tmdbestud.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel( get()) }
}
