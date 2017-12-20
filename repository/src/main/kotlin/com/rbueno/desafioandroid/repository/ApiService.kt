package com.rbueno.desafioandroid.repository

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler
import retrofit2.converter.gson.GsonConverterFactory

class ApiService private constructor(){

    private val repositoryService : RepositoryService

    companion object {
        val instance = ApiService()
    }

    init {

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(createWithScheduler(Schedulers.io()))
                .build()


        repositoryService =  retrofit.create(RepositoryService::class.java)
    }

    fun repositoryService() : RepositoryService = repositoryService
}