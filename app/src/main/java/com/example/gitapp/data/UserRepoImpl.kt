package com.example.gitapp.data

import com.example.gitapp.api.GitApi
import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepoImpl : UsersRepo {

    private val mapper = GitUserResponseToEntityMapper()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GIT_END_POINT)
        .build()
        .create(GitApi::class.java)

    override fun getUsers(callback: Callback<List<GitUserResponse>>) =
        api.getUsers().enqueue(callback)

    companion object {
        private const val GIT_END_POINT = "https://api.github.com/"
    }
}