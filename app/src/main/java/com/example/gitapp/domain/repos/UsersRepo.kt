package com.example.gitapp.domain.repos

import com.example.gitapp.api.GitUserResponse
import retrofit2.Callback

interface UsersRepo {

    fun getUsers(callback: Callback<List<GitUserResponse>>)
}