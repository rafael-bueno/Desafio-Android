package com.rbueno.desafioandroid.feature.pullrequest

import com.rbueno.desafioandroid.api.ApiConfig
import com.rbueno.desafioandroid.api.data.GitRepositoryPullRequest
import retrofit2.Call

fun fetchLisPullRequest(repositoryFullName: String, page: Int, itemsPerPage: Int): Call<List<GitRepositoryPullRequest>> {
    return ApiConfig.instance.apiService().listPullRequestPerRepo(repositoryFullName, page, itemsPerPage)
}