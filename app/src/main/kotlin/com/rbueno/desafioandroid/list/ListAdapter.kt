package com.rbueno.desafioandroid.list

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.repository.GitRepository
import de.hdodenhof.circleimageview.CircleImageView

class ListAdapter : PagedListAdapter<GitRepository, MainHolder>(DIFF) {
    override fun onBindViewHolder(holder: MainHolder?, position: Int) {
        holder?.bindView(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_repository_item, parent, false)
        return MainHolder(view)
    }
}


class MainHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textRepository = view.findViewById<TextView>(R.id.textRepositoryName)
    private val textRepositoryDescription = view.findViewById<TextView>(R.id.textRepositoryDescription)
    private val textRepositoryForks = view.findViewById<TextView>(R.id.textRepositoryForks)
    private val textRepositoryStars = view.findViewById<TextView>(R.id.textRepositoryStars)
    private val imageOwner = view.findViewById<CircleImageView>(R.id.imageOwner)
    private val textOwnerName = view.findViewById<TextView>(R.id.textOwnerName)

    fun bindView(repository: GitRepository?) {
        textRepository.text = repository?.repositoryName
        textRepositoryDescription.text = repository?.repositoryDescription
        textRepositoryForks.text = repository?.repositoryForksCount.toString()
        textRepositoryStars.text = repository?.repositoryStarsCount.toString()
        Glide.with(itemView).load(repository?.owner?.avatarUrl).into(imageOwner)
        textOwnerName.text = repository?.owner?.ownerLogin
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