package com.example.gitapp.ui

import com.example.gitapp.api.GitRepoResponse
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsPresenter(private val repo: UsersRepo) : UserDetailsContract.Presenter {

    private var view: UserDetailsContract.View? = null

    private val mapper = GitRepoResponseToEntityMapper()

    private var reposList: List<GitRepoEntity>? = null
    private var inProgress: Boolean = false

    override fun attach(view: UserDetailsContract.View) {
        this.view = view
        view.showProgressBar(inProgress)
        reposList?.let { view.showRepos(it) }
    }

    override fun detach() {
        view = null
    }

    override fun loadRepos(userName: String) {
        repo.getRepos(userName, callback)
    }

    private val callback = object : Callback<List<GitRepoResponse>> {
        override fun onResponse(
            call: Call<List<GitRepoResponse>>,
            response: Response<List<GitRepoResponse>>
        ) {
            if (response.isSuccessful) {
                inProgress = false
                view?.showProgressBar(inProgress)
                response.body()
                    ?.let {
                        val repoList = it.map { response -> mapper.map(response) }
                        view?.showRepos(repoList)
                        reposList = repoList
                    }
            }
        }

        override fun onFailure(call: Call<List<GitRepoResponse>>, t: Throwable) {
            inProgress = false
            view?.showProgressBar(inProgress)
            view?.showError()
        }
    }
}