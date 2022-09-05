package com.example.gitapp.domain.mappers

import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.domain.entities.GitUserEntity

class GitUserResponseToEntityMapper {
    fun map(response: GitUserResponse) =
        with(response) {
            GitUserEntity(login, id, avatarUrl)
        }
}