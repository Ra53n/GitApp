package com.example.gitapp

import android.app.Application
import android.content.Context
import com.example.gitapp.data.UserRepoImpl
import com.example.gitapp.domain.repos.UsersRepo

class App : Application() {
    val usersRepo: UsersRepo by lazy { UserRepoImpl() }
}

val Context.app: App get() = applicationContext as App