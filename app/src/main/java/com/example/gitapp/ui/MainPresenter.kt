package com.example.gitapp.ui

import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private val repo: UsersRepo) : UsersContract.Presenter {

    private var view: UsersContract.View? = null

    private val mapper = GitUserResponseToEntityMapper()

    override fun attach(view: UsersContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
    }

    override fun loadUsers() {
        view?.showProgressBar(true)
        repo.getUsers(callback)
    }

    private val callback = object : Callback<List<GitUserResponse>> {
        override fun onResponse(
            call: Call<List<GitUserResponse>>,
            response: Response<List<GitUserResponse>>
        ) {
            if (response.isSuccessful) {
                view?.showProgressBar(false)
                response.body()
                    ?.let { view?.showUsers(it.map { response -> mapper.map(response) }) }
            }
        }

        override fun onFailure(call: Call<List<GitUserResponse>>, t: Throwable) {
            view?.showProgressBar(false)
            view?.showError()
        }
    }
}