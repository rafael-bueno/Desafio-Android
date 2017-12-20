package com.rbueno.desafioandroid.repository

import android.arch.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoryService {

    @GET("search/repositories?q=language:Java&sort=stars")
    fun listRepositoryPerPage(@Query("page") page: Int, @Query("per_page") itemsPerPage: Int): LiveData<List<GitRepository>>

    @GET("repos/{repository}/pulls")
    fun listPullRequestPerRepo(@Path("repository") repositoryFullName: String): LiveData<List<GitRepositoryPullRequest>>
}