package com.rbueno.desafioandroid.feature.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.rbueno.desafioandroid.ListDividerDecoration
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.feature.pullrequest.PullRequestActivity
import kotlinx.android.synthetic.main.activity_list.*

const val EXTRA_REPO_NAME = "EXTRA_REPO_NAME"
class ListActivity : AppCompatActivity(), ListAdapterOnItemClickHandler {
    private lateinit var viewModel: ListActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        viewModel = ViewModelProviders.of(this).get(ListActivityViewModel::class.java)
        val adapter = ListAdapter(this)
        recyclerRepositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerRepositories.addItemDecoration(ListDividerDecoration(this))
        recyclerRepositories.adapter = adapter
        viewModel.repositories.observe(this, Observer { list -> adapter.setList(list) })
    }

    override fun onItemClick(repositoryFullName: String) {
        val intent = Intent(this, PullRequestActivity::class.java)
        intent.putExtra(EXTRA_REPO_NAME, repositoryFullName)
        startActivity(intent)
    }
}