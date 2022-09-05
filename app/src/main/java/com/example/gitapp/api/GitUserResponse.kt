package com.example.gitapp.api

import com.google.gson.annotations.SerializedName

data class GitUserResponse(
    val login: String,
    val id: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)