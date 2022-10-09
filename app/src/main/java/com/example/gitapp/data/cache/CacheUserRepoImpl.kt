package com.example.gitapp.data.cache

import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.CacheUserEntityMapper
import com.example.gitapp.domain.repos.CacheRepo
import io.reactivex.rxjava3.core.Observable

class CacheUserRepoImpl(
    private val database: CacheDatabase,
    private val mapper: CacheUserEntityMapper
) : CacheRepo {

    override fun getUsers(): Observable<List<GitUserEntity>> {
        return database.cacheDao().getAllUsers().map { it.map { entity -> mapper.map(entity) } }
    }

    override fun setUsers(users: List<GitUserEntity>) {
        database.cacheDao().deleteAllUsers()
        database.cacheDao().setUsers(users.map { mapper.map(it) })
    }

}