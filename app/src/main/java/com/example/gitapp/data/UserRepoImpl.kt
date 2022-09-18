package com.example.gitapp.data

import com.example.gitapp.BuildConfig
import com.example.gitapp.api.GitApi
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserRepoImpl : UsersRepo {

    private val userMapper = GitUserResponseToEntityMapper()

    private val repoMapper = GitRepoResponseToEntityMapper()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(GIT_END_POINT)
        .build()
        .create(GitApi::class.java)

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


    companion object {
        private const val GIT_END_POINT = "https://api.github.com/"
    }
}