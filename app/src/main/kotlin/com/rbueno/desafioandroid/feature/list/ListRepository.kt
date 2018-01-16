package com.rbueno.desafioandroid.feature.list

import com.rbueno.desafioandroid.api.ApiConfig
import com.rbueno.desafioandroid.api.data.GitRepositorySearch
import retrofit2.Call

fun fetchRepository(page: Int, itemsPerPage: Int): Call<GitRepositorySearch> {
    return ApiConfig.instance.apiService().listRepositoryPerPage(page, itemsPerPage)
}