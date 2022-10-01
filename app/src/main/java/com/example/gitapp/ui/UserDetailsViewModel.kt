package com.example.gitapp.ui

import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.repos.UsersRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class UserDetailsViewModel(private val repo: UsersRepo) : UserDetailsContract.ViewModel {

    override val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    override val reposLiveData: Observable<List<GitRepoEntity>> = BehaviorSubject.create()
    override val progressLiveData: Observable<Boolean> = BehaviorSubject.create()

    override fun loadRepos(userName: String) {
        repo.getRepos(userName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = ::dispatchGetReposSuccess,
                onError = ::dispatchError
            )
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