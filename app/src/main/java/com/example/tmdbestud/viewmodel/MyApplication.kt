package com.example.tmdbestud.viewmodel

import android.app.Application
import com.example.tmdbestud.di.appModule
import com.example.tmdbestud.di.repositoryModule
import com.example.tmdbestud.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext ( this@MyApplication)
            modules(listOf(repositoryModule, viewModelModule, appModule))
        }

    }

}