package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.core.Observable

interface UsersContract {

    interface ViewModel {
        val usersLiveData: Observable<List<GitUserEntity>>
        val progressLiveData: Observable<Boolean>
        val errorLiveData: Observable<Throwable>
        fun loadUsers()
    }
}