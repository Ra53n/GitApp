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

class MainActivity : AppCompatActivity(), UsersContract.View {

    private val adapter = UsersAdapter(object : UsersAdapter.Controller {
        override fun onUserClick(user: GitUserEntity) {
            val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
            val bundle = Bundle().apply { putSerializable(USER_BUNDLE_KEY, user) }
            intent.putExtras(bundle)
            startActivity(intent)
        }
    })

    private lateinit var binding: MainActivityBinding

    private lateinit var presenter: UsersContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        initPresenter()
    }

    private fun extractPresenter(): UsersContract.Presenter {
        return lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: MainPresenter(app.usersRepo)
    }

    override fun onRetainCustomNonConfigurationInstance(): UsersContract.Presenter {
        return presenter
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    private fun initPresenter() {
        presenter = MainPresenter(app.usersRepo)
        presenter = extractPresenter()
        presenter.attach(this)
        presenter.loadUsers()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun showUsers(users: List<GitUserEntity>) {
        adapter.setData(users)
    }

    override fun showError() {
        Toast.makeText(
            this,
            resources.getString(R.string.error_load_users),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }
}