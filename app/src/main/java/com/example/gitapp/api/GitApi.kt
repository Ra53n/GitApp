package com.example.gitapp.api

import retrofit2.Call
import retrofit2.http.GET

interface GitApi {

    @GET("users")
    fun getUsers(): Call<List<GitUserResponse>>
}