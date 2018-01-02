package com.rbueno.desafioandroid.pullrequest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import com.rbueno.desafioandroid.ListDividerDecoration
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.list.EXTRA_REPO_NAME
import kotlinx.android.synthetic.main.activity_pull_request.*

class PullRequestActivity : AppCompatActivity() {

    private lateinit var viewModel: PullRequestActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        recyclerPullRequests.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerPullRequests.addItemDecoration(ListDividerDecoration(this))
        val adapter = PullRequestAdapter()
        recyclerPullRequests.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(PullRequestActivityViewModel::class.java)
        viewModel.repositoryName = intent.extras.getString(EXTRA_REPO_NAME)
        viewModel.pullRequests.observe(this, Observer { list -> adapter.setList(list) })
    }

}
