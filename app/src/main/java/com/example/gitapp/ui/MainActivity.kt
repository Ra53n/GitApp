package com.example.gitapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.R
import com.example.gitapp.app
import com.example.gitapp.databinding.MainActivityBinding
import com.example.gitapp.domain.entities.GitUserEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private lateinit var viewModel: UsersContract.ViewModel

    private val compositeDisposable = CompositeDisposable()

    private val adapter = UsersAdapter(object : UsersAdapter.Controller {
        override fun onUserClick(user: GitUserEntity) {
            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
            val bundle = Bundle().apply { putSerializable(USER_BUNDLE_KEY, user) }
            intent.putExtras(bundle)
            startActivity(intent)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        initViewModel()
        viewModel.loadUsers()
    }

    private fun extractViewModel(): UsersContract.ViewModel {
        return lastCustomNonConfigurationInstance as? UsersContract.ViewModel
            ?: MainViewModel(app.usersRepo, app.cacheRepo)
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.ViewModel {
        return viewModel
    }

    private fun initViewModel() {
        viewModel = extractViewModel()
        compositeDisposable.addAll(
            viewModel.progressLiveData.subscribe { showProgressBar(it) },
            viewModel.usersLiveData.subscribe { showUsers(it) },
            viewModel.errorLiveData.subscribe { showError() }
        )
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun showUsers(users: List<GitUserEntity>) {
        adapter.setData(users)
    }

    private fun showError() {
        Toast.makeText(
            this,
            resources.getString(R.string.error_load_users),
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