package com.rbueno.desafioandroid.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.rbueno.desafioandroid.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private lateinit var viewModel : ListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_list)
        viewModel = ViewModelProviders.of(this).get(ListActivityViewModel::class.java)
        val adapter = MainAdapter()
        recyclerRepositories.adapter = adapter
        viewModel.repositories.observe(this, Observer { list -> adapter.setList(list) })
    }
}