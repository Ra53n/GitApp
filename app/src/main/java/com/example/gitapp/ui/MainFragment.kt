package com.example.gitapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapp.R
import com.example.gitapp.api.GitUserResponse
import com.example.gitapp.data.UserRepoImpl
import com.example.gitapp.databinding.MainFragmentBinding
import com.example.gitapp.domain.mappers.GitUserResponseToEntityMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment(R.layout.main_fragment) {

    private val adapter = UsersAdapter()

    private lateinit var binding: MainFragmentBinding

    private val repo = UserRepoImpl()

    private val mapper = GitUserResponseToEntityMapper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = MainFragmentBinding.bind(view)

        initRecyclerView()
        getUsers()
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getUsers() {
        Thread {
            Handler(Looper.getMainLooper()).post { showProgressBar(true) }
            repo.getUsers(callback)
        }.start()
    }

    private fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    private val callback = object : Callback<List<GitUserResponse>> {
        override fun onResponse(
            call: Call<List<GitUserResponse>>,
            response: Response<List<GitUserResponse>>
        ) {
            if (response.isSuccessful) {
                showProgressBar(false)
                response.body()
                    ?.let { adapter.setData(it.map { response -> mapper.map(response) }) }
            }
        }

        override fun onFailure(call: Call<List<GitUserResponse>>, t: Throwable) {
            showProgressBar(false)
            Toast.makeText(
                context,
                resources.getString(R.string.error_load_users),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}