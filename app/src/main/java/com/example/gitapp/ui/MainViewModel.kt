package com.example.gitapp.ui

import androidx.lifecycle.ViewModel
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.repos.CacheRepo
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repo: UsersRepo,
    private val cacheRepo: CacheRepo
) : UsersContract.ViewModel, ViewModel() {

    override val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    override val usersLiveData: Observable<List<GitUserEntity>> = BehaviorSubject.create()
    override val progressLiveData: Observable<Boolean> = BehaviorSubject.create()


    override fun loadUsers() {
        progressLiveData.mutable().onNext(true)
        repo.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = ::dispatchGetUsersSuccess,
                onError = ::dispatchError
            )
    }

    private fun dispatchGetUsersSuccess(list: List<GitUserEntity>) {
        progressLiveData.mutable().onNext(false)
        usersLiveData.mutable().onNext(list)
        Thread {
            cacheRepo.setUsers(list)
        }.start()
    }

    private fun dispatchError(error: Throwable) {
        progressLiveData.mutable().onNext(false)
        cacheRepo.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { usersLiveData.mutable().onNext(it) },
                onError = { errorLiveData.mutable().onNext(error) }
            )
        errorLiveData.mutable().onNext(error)
    }

    private fun <T : Any> Observable<T>.mutable(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("LiveData exception")
    }
}