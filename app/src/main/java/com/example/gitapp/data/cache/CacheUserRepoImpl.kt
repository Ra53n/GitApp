package com.example.gitapp.data.cache

import android.content.Context
import androidx.room.Room
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.CacheUserEntityMapper
import com.example.gitapp.domain.repos.CacheRepo
import io.reactivex.rxjava3.core.Observable

class CacheUserRepoImpl(private val context: Context) : CacheRepo {

    companion object {
        const val CACHE_USERS_DATABASE = "CACHE_USERS_DATABASE"
    }

    private val mapper = CacheUserEntityMapper()

    private val database =
        Room.databaseBuilder(
            context, CacheDatabase::class.java,
            CACHE_USERS_DATABASE
        ).build()

    override fun getUsers(): Observable<List<GitUserEntity>> {
        return database.cacheDao().getAllUsers().map { it.map { entity -> mapper.map(entity) } }
    }

    override fun setUsers(users: List<GitUserEntity>) {
        database.cacheDao().deleteAllUsers()
        database.cacheDao().setUsers(users.map { mapper.map(it) })
    }
}