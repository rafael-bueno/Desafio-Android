package com.rbueno.desafioandroid.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.rbueno.desafioandroid.ListDividerDecoration
import com.rbueno.desafioandroid.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    private lateinit var viewModel: ListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        viewModel = ViewModelProviders.of(this).get(ListActivityViewModel::class.java)
        val adapter = ListAdapter()
        recyclerRepositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerRepositories.addItemDecoration(ListDividerDecoration(this))
        recyclerRepositories.adapter = adapter
        viewModel.repositories.observe(this, Observer { list -> adapter.setList(list) })
    }
}