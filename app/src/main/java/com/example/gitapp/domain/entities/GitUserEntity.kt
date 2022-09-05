package com.example.gitapp.domain.entities

import java.io.Serializable

data class GitUserEntity(
    val login: String,
    val id: String,
    val avatarUrl: String
) : Serializable