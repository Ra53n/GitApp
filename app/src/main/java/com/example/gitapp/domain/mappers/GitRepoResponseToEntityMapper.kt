package com.example.gitapp.domain.mappers

import com.example.gitapp.api.GitRepoResponse
import com.example.gitapp.domain.entities.GitRepoEntity

class GitRepoResponseToEntityMapper {

    fun map(response: GitRepoResponse): GitRepoEntity {
        return with(response) {
            GitRepoEntity(
                id, name, if (description.isNullOrEmpty()) {
                    "No description."
                } else {
                    description
                }, forks
            )
        }
    }
}