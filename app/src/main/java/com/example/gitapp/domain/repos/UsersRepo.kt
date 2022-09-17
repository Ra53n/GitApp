package com.example.gitapp.domain.repos

import com.example.gitapp.api.GitRepoResponse
import com.example.gitapp.api.GitUserResponse
import retrofit2.Callback

interface UsersRepo {

    fun getUsers(callback: Callback<List<GitUserResponse>>)

    fun getRepos(userName: String, callback: Callback<List<GitRepoResponse>>)
}