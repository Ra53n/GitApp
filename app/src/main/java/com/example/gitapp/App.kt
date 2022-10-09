package com.example.gitapp

import android.app.Application
import android.content.Context
import com.example.gitapp.di.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}

val Context.app: App get() = applicationContext as App