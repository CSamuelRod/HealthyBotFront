package com.example.healthybotfront

import android.app.Application

import com.example.healthybotfront.di.appModule
import com.example.healthybotfront.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                appModule,
                retrofitModule
            )
        }
    }
}