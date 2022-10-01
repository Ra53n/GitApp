package com.example.gitapp.data

import com.example.gitapp.BuildConfig
import com.example.gitapp.api.GitApi
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.core.Single

class UserRepoImpl(
    private val api: GitApi,
    private val userMapper: GitUserResponseToEntityMapper,
    private val repoMapper: GitRepoResponseToEntityMapper
) : UsersRepo {

    override fun getUsers(): Single<List<GitUserEntity>> =
        api.getUsers(BuildConfig.GIT_TOKEN)
            .map { list ->
                list.map { userMapper.map(it) }
            }

    override fun getRepos(userName: String): Single<List<GitRepoEntity>> =
        api.getRepos(userName, BuildConfig.GIT_TOKEN)
            .map { list ->
                list.map { repoMapper.map(it) }
            }
}