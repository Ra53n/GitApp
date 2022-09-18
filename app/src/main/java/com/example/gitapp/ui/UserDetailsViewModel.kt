package com.example.gitapp.ui

import SingleEventLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitapp.api.GitRepoResponse
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.mappers.GitRepoResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.kotlin.subscribeBy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel(private val repo: UsersRepo) : UserDetailsContract.ViewModel {

    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val reposLiveData: LiveData<List<GitRepoEntity>> = MutableLiveData()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData()

    override fun loadRepos(userName: String) {
        repo.getRepos(userName)
            .subscribeBy(
                onSuccess = ::dispatchGetReposSuccess,
                onError = ::dispatchError
            )
    }

    private fun dispatchGetReposSuccess(list: List<GitRepoEntity>) {
        progressLiveData.mutable().postValue(false)
        reposLiveData.mutable().postValue(list)
    }

    private fun dispatchError(error: Throwable) {
        progressLiveData.mutable().postValue(false)
        errorLiveData.mutable().postValue(error)
    }

    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("LiveData exception")
    }
}