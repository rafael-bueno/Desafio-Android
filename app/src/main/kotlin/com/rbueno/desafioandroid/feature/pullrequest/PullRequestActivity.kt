package com.rbueno.desafioandroid.feature.pullrequest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.View
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.base.ListDividerDecoration
import com.rbueno.desafioandroid.feature.list.EXTRA_REPO_NAME
import kotlinx.android.synthetic.main.activity_pull_request.*

class PullRequestActivity : AppCompatActivity() {

    private lateinit var viewModel: PullRequestActivityViewModel
    private val adapter = PullRequestAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        recyclerPullRequests.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        recyclerPullRequests.addItemDecoration(ListDividerDecoration(this))
        recyclerPullRequests.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this,
                PullRequestActivityViewModelFactory(intent.extras.getString(EXTRA_REPO_NAME))).get(PullRequestActivityViewModel::class.java)

        with(viewModel) {
            loading.observe(this@PullRequestActivity, Observer {
                configLoading(it)
            })

            error.observe(this@PullRequestActivity, Observer { showSnackError() })

            pullRequests.observe(this@PullRequestActivity,
                    Observer { list -> adapter.setList(list) })
        }
    }

    private fun showSnackError() {
        val snackBar = Snackbar.make(framePullRequestActivity, R.string.error, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    private fun configLoading(show: Boolean?) = if (show == true) {
        loading.visibility = View.VISIBLE
    } else {
        loading.visibility = View.GONE
    }

}
