package com.rbueno.desafioandroid.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.repository.GitRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        val adapter = MainAdapter()
        recyclerRepositories.adapter = adapter

        viewModel.repositories.observe(this, Observer { list -> adapter.setList(list as PagedList<GitRepository>?) })
    }
}