package com.example.gitapp.ui

import SingleEventLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.kotlin.subscribeBy

class MainViewModel(private val repo: UsersRepo) : UsersContract.ViewModel {

    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val usersLiveData: LiveData<List<GitUserEntity>> = MutableLiveData()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData()


    override fun loadUsers() {
        progressLiveData.mutable().postValue(true)
        repo.getUsers()
            .subscribeBy(
                onSuccess = ::dispatchGetUsersSuccess,
                onError = ::dispatchError
            )
    }

    private fun dispatchGetUsersSuccess(list: List<GitUserEntity>) {
        progressLiveData.mutable().postValue(false)
        usersLiveData.mutable().postValue(list)
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