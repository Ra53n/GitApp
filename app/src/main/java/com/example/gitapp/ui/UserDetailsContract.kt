package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitRepoEntity
import io.reactivex.rxjava3.core.Observable

interface UserDetailsContract {

    interface ViewModel {
        val reposLiveData: Observable<List<GitRepoEntity>>
        val progressLiveData: Observable<Boolean>
        val errorLiveData: Observable<Throwable>
        fun loadRepos(userName: String)
    }
}