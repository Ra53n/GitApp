package com.example.gitapp.ui

import SingleEventLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitapp.api.GitRepoResponse
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel(private val repo: UsersRepo) : UserDetailsContract.ViewModel {

    private val mapper = GitRepoResponseToEntityMapper()

    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val reposLiveData: LiveData<List<GitRepoEntity>> = MutableLiveData()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData()

    override fun loadRepos(userName: String) {
        repo.getRepos(userName, callback)
    }

    private val callback = object : Callback<List<GitRepoResponse>> {
        override fun onResponse(
            call: Call<List<GitRepoResponse>>,
            response: Response<List<GitRepoResponse>>
        ) {
            if (response.isSuccessful) {
                progressLiveData.mutable().postValue(false)
                response.body()
                    ?.let {
                        val repoList = it.map { response -> mapper.map(response) }
                        reposLiveData.mutable().postValue(repoList)
                    }
            }
        }

        override fun onFailure(call: Call<List<GitRepoResponse>>, t: Throwable) {
            progressLiveData.mutable().postValue(false)
            errorLiveData.mutable().postValue(t)
        }
    }


    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("LiveData exception")
    }
}