package com.example.gitapp.domain.repos

import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.core.Single

interface UsersRepo {

    fun getUsers(): Single<List<GitUserEntity>>

    fun getRepos(userName: String): Single<List<GitRepoEntity>>
}