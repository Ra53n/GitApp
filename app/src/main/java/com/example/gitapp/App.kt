package com.example.gitapp

import android.app.Application
import android.content.Context
import com.example.gitapp.di.provideAppModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        provideAppModule(applicationContext).install()
    }
}

val Context.app: App get() = applicationContext as App