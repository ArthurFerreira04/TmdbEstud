package com.example.tmdbestud.di

import com.example.tmdbestud.repository.DataRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { DataRepository(moviesApi = get()) }
}
