package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitUserEntity

interface UsersContract {
    interface View {
        fun showUsers(users: List<GitUserEntity>)
        fun showError()
        fun showProgressBar(isVisible: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()

        fun loadUsers()
    }
}