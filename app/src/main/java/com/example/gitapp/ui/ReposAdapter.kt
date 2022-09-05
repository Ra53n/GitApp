package com.example.gitapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.R
import com.example.gitapp.databinding.RepoItemBinding
import com.example.gitapp.domain.entities.GitRepoEntity

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    private var list = listOf<GitRepoEntity>()

    fun setData(list: List<GitRepoEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReposViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        )

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RepoItemBinding.bind(itemView)
        fun bind(repo: GitRepoEntity) {
            binding.title.text = repo.name
            binding.description.text = repo.description
            binding.forks.text = "Forks: ${repo.forks.toString()}"
        }
    }
}