package com.example.gitapp.domain.repos

import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.core.Observable

interface CacheRepo {
    fun getUsers(): Observable<List<GitUserEntity>>

    fun setUsers(users: List<GitUserEntity>)
}