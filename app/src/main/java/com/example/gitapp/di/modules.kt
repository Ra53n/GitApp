package com.example.gitapp.di

import android.content.Context
import androidx.room.Room
import com.example.di.Module
import com.example.di.bindToInstance
import com.example.di.bindToSingleton
import com.example.di.get
import com.example.gitapp.api.GitApi
import com.example.gitapp.data.UserRepoImpl
import com.example.gitapp.data.cache.CacheDatabase
import com.example.gitapp.data.cache.CacheUserRepoImpl
import com.example.gitapp.domain.mappers.CacheUserEntityMapper
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.CacheRepo
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun provideAppModule(context: Context) = Module {
    val CACHE_USERS_DATABASE = "CACHE_USERS_DATABASE"
    val GIT_END_POINT = "https://api.github.com/"

    (Context::class).bindToSingleton { context }

    (GitUserResponseToEntityMapper::class).bindToInstance()

    (GitRepoResponseToEntityMapper::class).bindToInstance()

    (CacheUserEntityMapper::class).bindToInstance()

    Retrofit::class.bindToSingleton {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(GIT_END_POINT)
            .build()
    }

    (UsersRepo::class).bindToSingleton { UserRepoImpl(get(), get(), get()) }

    (GitApi::class).bindToSingleton {
        get<Retrofit>().create(GitApi::class.java)
    }

    (CacheDatabase::class).bindToSingleton {
        Room.databaseBuilder(
            get(),
            CacheDatabase::class.java,
            CACHE_USERS_DATABASE
        ).build()
    }

    (UsersRepo::class).bindToSingleton {
        UserRepoImpl(get(), get(), get())
    }

    (CacheRepo::class).bindToSingleton {
        CacheUserRepoImpl(get(), get())
    }
}