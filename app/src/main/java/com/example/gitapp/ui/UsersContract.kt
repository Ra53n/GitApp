package com.example.gitapp.ui

import androidx.lifecycle.LiveData
import com.example.gitapp.domain.entities.GitUserEntity

interface UsersContract {

    interface ViewModel {
        val usersLiveData: LiveData<List<GitUserEntity>>
        val progressLiveData: LiveData<Boolean>
        val errorLiveData: LiveData<Throwable>
        fun loadUsers()
    }
}