package com.rbueno.desafioandroid.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.rbueno.desafioandroid.repository.GitRepository


class MainActivityViewModel : ViewModel() {

    lateinit var repositories: LiveData<List<GitRepository>>

    fun getRepositoryList() {

    }

}