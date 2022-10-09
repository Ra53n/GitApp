package com.example.gitapp.di

import com.example.gitapp.App
import com.example.gitapp.ui.MainActivity
import com.example.gitapp.ui.UserDetailsActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(userDetailsActivity: UserDetailsActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}