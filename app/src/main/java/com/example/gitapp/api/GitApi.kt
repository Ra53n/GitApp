package com.example.gitapp.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GitApi {

    @GET("users")
    fun getUsers(@Header("Authorization") token: String): Single<List<GitUserResponse>>

    @GET("users/{user}/repos")
    fun getRepos(
        @Path("user") userName: String,
        @Header("Authorization") token: String
    ): Single<List<GitRepoResponse>>
}