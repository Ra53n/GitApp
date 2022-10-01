package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.core.Observable

interface UserDetailsContract {

    interface ViewModel {
        val userLiveData: Observable<GitUserEntity>
        val reposLiveData: Observable<List<GitRepoEntity>>
        val progressLiveData: Observable<Boolean>
        val errorLiveData: Observable<Throwable>
        fun loadRepos()
        fun bindData()
    }
}