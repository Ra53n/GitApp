package com.example.gitapp.di

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
import com.example.gitapp.ui.MainViewModel
import com.example.gitapp.ui.UserDetailsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    val CACHE_USERS_DATABASE = "CACHE_USERS_DATABASE"
    val GIT_END_POINT = "https://api.github.com/"

    single { GitUserResponseToEntityMapper() }
    single { GitRepoResponseToEntityMapper() }
    single { CacheUserEntityMapper() }

    single {
        Room.databaseBuilder(
            androidContext(),
            CacheDatabase::class.java,
            CACHE_USERS_DATABASE
        ).build()
    }
    single<GitApi> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(GIT_END_POINT)
            .build()
            .create(GitApi::class.java)
    }

    single<UsersRepo> { UserRepoImpl(get(), get(), get()) }
    single<CacheRepo> { CacheUserRepoImpl(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
    viewModel { UserDetailsViewModel(get()) }
}