package com.rbueno.desafioandroid.feature.pullrequest

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.repository.GitRepositoryPullRequest
import de.hdodenhof.circleimageview.CircleImageView

class PullRequestAdapter : PagedListAdapter<GitRepositoryPullRequest, GitRepositoryPullRequestHolder>(DIFF) {
    override fun onBindViewHolder(holder: GitRepositoryPullRequestHolder?, position: Int) {
        holder?.bindView(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GitRepositoryPullRequestHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.view_pullrequest_item,
                parent, false)
        return GitRepositoryPullRequestHolder(view)
    }
}

class GitRepositoryPullRequestHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textPullRequestTitle = view.findViewById<TextView>(R.id.textPullRequestTitle)
    private val textPullRequestDescription = view.findViewById<TextView>(R.id.textPullRequestDescription)
    private val imagePullRequestAuthor = view.findViewById<CircleImageView>(R.id.imagePullRequestAuthor)
    private val textAuthorName = view.findViewById<TextView>(R.id.textAuthorName)

    fun bindView(item: GitRepositoryPullRequest?) {
        textPullRequestTitle.text = item?.pullRequestTitle
        textPullRequestDescription.text = item?.pullRequestDescription
        textAuthorName.text = item?.head?.user?.userName
        Glide.with(itemView).load(item?.head?.user?.avatarUrl).into(imagePullRequestAuthor)
    }
}

object DIFF : DiffCallback<GitRepositoryPullRequest>() {
    override fun areItemsTheSame(oldItem: GitRepositoryPullRequest, newItem: GitRepositoryPullRequest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GitRepositoryPullRequest, newItem: GitRepositoryPullRequest): Boolean {
        return oldItem == newItem
    }
}