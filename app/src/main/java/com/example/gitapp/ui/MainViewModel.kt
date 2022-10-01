package com.example.gitapp.ui

import SingleEventLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import com.example.gitapp.domain.repos.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repo: UsersRepo) : UsersContract.ViewModel {

    private val mapper = GitUserResponseToEntityMapper()

    override val errorLiveData: LiveData<Throwable> = SingleEventLiveData()
    override val usersLiveData: LiveData<List<GitUserEntity>> = MutableLiveData()
    override val progressLiveData: LiveData<Boolean> = MutableLiveData()


    override fun loadUsers() {
        progressLiveData.mutable().postValue(true)
        repo.getUsers(callback)
    }

    private val callback = object : Callback<List<GitUserResponse>> {
        override fun onResponse(
            call: Call<List<GitUserResponse>>,
            response: Response<List<GitUserResponse>>
        ) {
            if (response.isSuccessful) {
                progressLiveData.mutable().postValue(false)
                response.body()
                    ?.let {
                        val userList = it.map { response -> mapper.map(response) }
                        usersLiveData.mutable().postValue(userList)
                    }
            }
        }

        override fun onFailure(call: Call<List<GitUserResponse>>, t: Throwable) {
            progressLiveData.mutable().postValue(false)
            errorLiveData.mutable().postValue(t)
        }
    }


    private fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("LiveData exception")
    }
}