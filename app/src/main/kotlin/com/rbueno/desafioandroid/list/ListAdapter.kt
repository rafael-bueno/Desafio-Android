package com.rbueno.desafioandroid.list

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.repository.GitRepository

class MainAdapter : PagedListAdapter<GitRepository, MainHolder>(DIFF) {
    override fun onBindViewHolder(holder: MainHolder?, position: Int) {
        holder?.bindView(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_repository_item, parent, false)
        return MainHolder(view)
    }
}


class MainHolder(view: View) : RecyclerView.ViewHolder(view) {

    val textRepository : TextView

    init {
        textRepository = view.findViewById<TextView>(R.id.textRepositoryName)
    }

    fun bindView(repository: GitRepository?) {
        textRepository.text = repository?.repositoryName
    }

}

object DIFF : DiffCallback<GitRepository>() {
    override fun areItemsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem.repositoryName.equals(newItem.repositoryName)
    }

    override fun areContentsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem == newItem
    }
}