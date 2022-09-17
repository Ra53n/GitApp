package com.example.gitapp.api

data class GitRepoResponse(
    val id: String,
    val name: String,
    val description: String?,
    val forks: Int
)
