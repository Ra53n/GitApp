package com.example.gitapp.ui

import androidx.lifecycle.LiveData
import com.example.gitapp.domain.entities.GitRepoEntity

interface UserDetailsContract {

    interface ViewModel {
        val reposLiveData: LiveData<List<GitRepoEntity>>
        val progressLiveData: LiveData<Boolean>
        val errorLiveData: LiveData<Throwable>
        fun loadRepos(userName: String)
    }
}