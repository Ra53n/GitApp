package com.example.gitapp.ui

import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val repo: UsersRepo) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    private val mapper = GitUserResponseToEntityMapper()

    private var usersList: List<GitUserEntity>? = null
    private var inProgress: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgressBar(inProgress)
        usersList?.let { view.showUsers(it) }
    }

    override fun detach() {
        view = null
    }

    override fun loadUsers() {
        inProgress = true
        view?.showProgressBar(inProgress)
        repo.getUsers(callback)
    }

    private val callback = object : Callback<List<GitUserResponse>> {
        override fun onResponse(
            call: Call<List<GitUserResponse>>,
            response: Response<List<GitUserResponse>>
        ) {
            if (response.isSuccessful) {
                inProgress = false
                view?.showProgressBar(inProgress)
                response.body()
                    ?.let {
                        val userList = it.map { response -> mapper.map(response) }
                        view?.showUsers(userList)
                        usersList = userList
                    }
            }
        }

        override fun onFailure(call: Call<List<GitUserResponse>>, t: Throwable) {
            inProgress = false
            view?.showProgressBar(inProgress)
            view?.showError()
        }
    }
}