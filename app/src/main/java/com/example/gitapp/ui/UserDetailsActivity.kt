package com.example.gitapp.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.gitapp.R
import com.example.gitapp.app
import com.example.gitapp.databinding.UserDetailsActivityBinding
import com.example.gitapp.domain.entities.GitRepoEntity
import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable

const val USER_BUNDLE_KEY = "USER_BUNDLE_KEY"

class UserDetailsActivity : AppCompatActivity() {
    lateinit var user: GitUserEntity

    private val adapter = ReposAdapter()

    private lateinit var binding: UserDetailsActivityBinding

    private lateinit var viewModel: UserDetailsContract.ViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = UserDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getSerializable(USER_BUNDLE_KEY)?.let {
            user = it as GitUserEntity
        }

        initRecyclerView()
        bindData()
        initViewModel()

        viewModel.loadRepos(user.login)

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putSerializable(USER_BUNDLE_KEY, user)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getSerializable(USER_BUNDLE_KEY)?.let { user = it as GitUserEntity }
    }

    private fun extractViewModel(): UserDetailsContract.ViewModel {
        return lastCustomNonConfigurationInstance as? UserDetailsContract.ViewModel
            ?: UserDetailsViewModel(app.usersRepo)
    }

    override fun onRetainCustomNonConfigurationInstance(): UserDetailsContract.ViewModel {
        return viewModel
    }

    private fun bindData() {
        binding.avatar.load(user.avatarUrl)
        binding.loginTextView.text = user.login
        binding.uidTextView.text = user.id
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        viewModel = extractViewModel()

        compositeDisposable.addAll(
            viewModel.progressLiveData.subscribe { showProgressBar(it) },
            viewModel.reposLiveData.subscribe { showRepos(it) },
            viewModel.errorLiveData.subscribe { showError() }
        )
    }


    private fun showRepos(repos: List<GitRepoEntity>) {
        adapter.setData(repos)
    }

    private fun showError() {
        Toast.makeText(
            this,
            resources.getString(R.string.error_load_repos),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}