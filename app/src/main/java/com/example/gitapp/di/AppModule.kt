package com.example.gitapp.di

import android.content.Context
import androidx.room.Room
import com.example.gitapp.App
import com.example.gitapp.api.GitApi
import com.example.gitapp.data.UserRepoImpl
import com.example.gitapp.data.cache.CacheDatabase
import com.example.gitapp.data.cache.CacheUserRepoImpl
import com.example.gitapp.domain.mappers.CacheUserEntityMapper
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.CacheRepo
import com.example.gitapp.domain.repos.UsersRepo
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    val CACHE_USERS_DATABASE = "CACHE_USERS_DATABASE"
    val GIT_END_POINT = "https://api.github.com/"


    @Singleton
    @Provides
    fun provideGitUserMapper(): GitUserResponseToEntityMapper {
        return GitUserResponseToEntityMapper()
    }

    @Singleton
    @Provides
    fun provideGitRepoMapper(): GitRepoResponseToEntityMapper {
        return GitRepoResponseToEntityMapper()
    }

    @Singleton
    @Provides
    fun provideCacheUserMapper(): CacheUserEntityMapper {
        return CacheUserEntityMapper()
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(GIT_END_POINT)
            .build()
    }

    @Singleton
    @Provides
    fun provideGitApi(retrofit: Retrofit): GitApi {
        return retrofit.create(GitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCacheDatabase(context: Context): CacheDatabase {
        return Room.databaseBuilder(
            context,
            CacheDatabase::class.java,
            CACHE_USERS_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideUsersRepo(
        api: GitApi,
        userMapper: GitUserResponseToEntityMapper,
        repoMapper: GitRepoResponseToEntityMapper
    ): UsersRepo {
        return UserRepoImpl(api, userMapper, repoMapper)
    }

    @Singleton
    @Provides
    fun provideCacheRepo(
        database: CacheDatabase,
        mapper: CacheUserEntityMapper
    ): CacheRepo {
        return CacheUserRepoImpl(database, mapper)
    }

    @Provides
    fun provideContext(app: App): Context = app
}