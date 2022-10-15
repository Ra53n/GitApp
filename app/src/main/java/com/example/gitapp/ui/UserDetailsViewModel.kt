package com.example.gitapp.ui

import androidx.lifecycle.ViewModel
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class UserDetailsViewModel constructor(
    private val repo: UsersRepo
) : UserDetailsContract.ViewModel, ViewModel() {

    override val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    override val reposLiveData: Observable<List<GitRepoEntity>> = BehaviorSubject.create()
    override val progressLiveData: Observable<Boolean> = BehaviorSubject.create()
    override val userLiveData: Observable<GitUserEntity> = BehaviorSubject.create()

    private lateinit var user: GitUserEntity

    override fun loadRepos() {
        repo.getRepos(user.login)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = ::dispatchGetReposSuccess,
                onError = ::dispatchError
            )
    }

    override fun bindData(user: GitUserEntity) {
        this.user = user
        userLiveData.mutable().onNext(user)
    }

    private fun dispatchGetReposSuccess(list: List<GitRepoEntity>) {
        progressLiveData.mutable().onNext(false)
        reposLiveData.mutable().onNext(list)
    }

    private fun dispatchError(error: Throwable) {
        progressLiveData.mutable().onNext(false)
        errorLiveData.mutable().onNext(error)
    }

    private fun <T : Any> Observable<T>.mutable(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("LiveData exception")
    }
}