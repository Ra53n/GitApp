package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitRepoEntity

interface UserDetailsContract {

    interface View {
        fun showRepos(repos: List<GitRepoEntity>)
        fun showError()
        fun showProgressBar(isVisible: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun loadRepos(userName: String)
    }
}