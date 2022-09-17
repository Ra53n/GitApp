package com.example.gitapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.gitapp.R
import com.example.gitapp.databinding.UserItemBinding
import com.example.gitapp.domain.entities.GitUserEntity

class UsersAdapter(private val controller: Controller) :
    RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {

    interface Controller {
        fun onUserClick(user: GitUserEntity)
    }

    private var list = listOf<GitUserEntity>()

    fun setData(list: List<GitUserEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemBinding.bind(itemView)
        fun bind(user: GitUserEntity) {
            binding.avatar.load(user.avatarUrl)
            binding.loginTextView.text = user.login
            binding.uidTextView.text = user.id
            binding.card.setOnClickListener { controller.onUserClick(user) }
        }
    }
}