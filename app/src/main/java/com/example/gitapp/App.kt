package com.example.gitapp

import android.app.Application
import android.content.Context
import androidx.room.Room
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

class App : Application() {

    private val GIT_END_POINT = "https://api.github.com/"

    private val CACHE_USERS_DATABASE = "CACHE_USERS_DATABASE"

    private val userMapper = GitUserResponseToEntityMapper()

    private val repoMapper = GitRepoResponseToEntityMapper()

    private val cacheMapper = CacheUserEntityMapper()

    private val database by lazy {
        Room.databaseBuilder(
            this,
            CacheDatabase::class.java,
            CACHE_USERS_DATABASE
        ).build()
    }

    private val api by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(GIT_END_POINT)
            .build()
            .create(GitApi::class.java)
    }

    val usersRepo: UsersRepo by lazy { UserRepoImpl(api, userMapper, repoMapper) }
    val cacheRepo: CacheRepo by lazy { CacheUserRepoImpl(database, cacheMapper) }
}

val Context.app: App get() = applicationContext as App