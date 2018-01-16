package com.rbueno.desafioandroid.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig private constructor() {

    private val apiService: ApiService

    companion object {
        val instance = ApiConfig()
    }

    init {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        apiService = retrofit.create(ApiService::class.java)
    }

    fun apiService(): ApiService = apiService
}