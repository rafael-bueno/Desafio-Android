package com.rbueno.desafioandroid.repository

import com.google.gson.annotations.SerializedName

data class GitRepositorySearch(@SerializedName("total_count") val totalItems: Int, @SerializedName("items") val items: List<GitRepository>)

data class GitRepository(@SerializedName("name") val repositoryName: String,
                         @SerializedName("description") val repositoryDescription: String,
                         @SerializedName("forks_count") val repositoryForksCount: Int,
                         @SerializedName("stargazers_count") val repositoryStarsCount: Int,
                         @SerializedName("full_name") val repositoryUserName: String,
                         @SerializedName("owner") val owner: GitRepositoryOwner)

data class GitRepositoryOwner(@SerializedName("login") val ownerLogin: String,
                              @SerializedName("avatar_url") val avatarUrl: String)


data class GitRepositoryPullRequest(@SerializedName("id") val id: Int,
                                    @SerializedName("title") val pullRequestTitle: String,
                                    @SerializedName("body") val pullRequestDescription: String,
                                    @SerializedName("head") val head: GitRepositoryPullRequestHead)


data class GitRepositoryPullRequestHead(@SerializedName("user") val user: GitRepositoryPullRequestUser)

data class GitRepositoryPullRequestUser(@SerializedName("login") val userName: String,
                                        @SerializedName("avatar_url") val avatarUrl: String)
