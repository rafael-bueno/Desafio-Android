package com.rbueno.desafioandroid.api

import com.rbueno.desafioandroid.api.data.GitRepositoryPullRequest
import com.rbueno.desafioandroid.api.data.GitRepositorySearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories?q=language:Java&sort=stars")
    fun listRepositoryPerPage(@Query("page") page: Int, @Query("per_page") itemsPerPage: Int): Call<GitRepositorySearch>

    @GET("repos/{repository}/pulls")
    fun listPullRequestPerRepo(@Path("repository", encoded = true) repositoryFullName: String,
                               @Query("page") page: Int, @Query("per_page") itemsPerPage: Int): Call<List<GitRepositoryPullRequest>>

}