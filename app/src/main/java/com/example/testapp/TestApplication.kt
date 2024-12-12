package com.example.testapp

import android.app.Application
import com.example.testapp.util.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApplication)
            modules(appModule)
        }
    }
}