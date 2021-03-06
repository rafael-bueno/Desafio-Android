package com.rbueno.desafioandroid.feature.list

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.api.data.GitRepository
import de.hdodenhof.circleimageview.CircleImageView

class ListAdapter(private val callbackHandler: ListAdapterOnItemClickHandler) :
        PagedListAdapter<GitRepository, GitRepositoryHolder>(DIFF) {
    override fun onBindViewHolder(holder: GitRepositoryHolder?, position: Int) {
        holder?.bindView(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitRepositoryHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_repository_item,
                parent, false)
        return GitRepositoryHolder(view, callbackHandler)
    }
}

interface ListAdapterOnItemClickHandler {
    fun onItemClick(repositoryFullName: String)
}

class GitRepositoryHolder(view: View, private val callbackHandler: ListAdapterOnItemClickHandler)
    : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val textRepository = view.findViewById<TextView>(R.id.textRepositoryName)
    private val textRepositoryDescription = view.findViewById<TextView>(R.id.textRepositoryDescription)
    private val textRepositoryForks = view.findViewById<TextView>(R.id.textRepositoryForks)
    private val textRepositoryStars = view.findViewById<TextView>(R.id.textRepositoryStars)
    private val imageOwner = view.findViewById<CircleImageView>(R.id.imageOwner)
    private val textOwnerName = view.findViewById<TextView>(R.id.textOwnerName)

    fun bindView(repository: GitRepository?) {
        if (repository != null) {
            textRepository.text = repository.repositoryName
            textRepositoryDescription.text = repository.repositoryDescription
            textRepositoryForks.text = repository.repositoryForksCount.toString()
            textRepositoryStars.text = repository.repositoryStarsCount.toString()
            Glide.with(itemView).load(repository.owner.avatarUrl).into(imageOwner)
            textOwnerName.text = repository.owner.ownerLogin

            itemView.tag = repository.repositoryUserName
            itemView.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            callbackHandler.onItemClick(v.tag as String)
        }
    }

}

object DIFF : DiffCallback<GitRepository>() {
    override fun areItemsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean {
        return oldItem == newItem
    }
}