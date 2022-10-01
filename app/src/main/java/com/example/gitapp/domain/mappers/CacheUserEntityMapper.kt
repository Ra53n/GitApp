package com.example.gitapp.domain.mappers

import com.example.gitapp.data.cache.CacheUserEntity
import com.example.gitapp.domain.entities.GitUserEntity

class CacheUserEntityMapper {

    fun map(gitUserEntity: GitUserEntity): CacheUserEntity {
        return with(gitUserEntity) {
            CacheUserEntity(login, id, avatarUrl)
        }
    }

    fun map(cacheUserEntity: CacheUserEntity): GitUserEntity {
        return with(cacheUserEntity) {
            GitUserEntity(login, id, avatarUrl)
        }
    }
}