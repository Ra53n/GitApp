package com.example.gitapp.domain.entities

data class GitRepoEntity(
    val id: String,
    val name: String,
    val description: String?,
    val forks: Int
)