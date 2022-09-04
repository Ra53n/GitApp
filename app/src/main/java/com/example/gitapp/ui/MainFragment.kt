package com.example.gitapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.R
import com.example.gitapp.app
import com.example.gitapp.data.UserRepoImpl
import com.example.gitapp.databinding.MainFragmentBinding
import com.example.gitapp.domain.entities.GitUserEntity

class MainFragment : Fragment(R.layout.main_fragment), UsersContract.View {

    private val adapter = UsersAdapter()

    private lateinit var binding: MainFragmentBinding

    private lateinit var presenter: UsersContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = MainFragmentBinding.bind(view)

        initRecyclerView()

        initPresenter()
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    private fun initPresenter() {
        context?.app?.usersRepo?.let {
            presenter = MainPresenter(it)
            presenter.attach(this)
            presenter.loadUsers()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun showUsers(users: List<GitUserEntity>) {
        adapter.setData(users)
    }

    override fun showError() {
        Toast.makeText(
            context,
            resources.getString(R.string.error_load_users),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }
}