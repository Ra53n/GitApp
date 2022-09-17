package com.example.gitapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GitApi {

    @GET("users")
    fun getUsers(@Header("Authorization") token: String): Call<List<GitUserResponse>>

    @GET("users/{user}/repos")
    fun getRepos(
        @Path("user") userName: String,
        @Header("Authorization") token: String
    ): Call<List<GitRepoResponse>>
}