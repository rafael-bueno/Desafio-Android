package com.rbueno.desafioandroid.feature.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.rbueno.desafioandroid.R
import com.rbueno.desafioandroid.base.ListDividerDecoration
import com.rbueno.desafioandroid.feature.pullrequest.PullRequestActivity
import kotlinx.android.synthetic.main.activity_list.*


const val EXTRA_REPO_NAME = "EXTRA_REPO_NAME"

class ListActivity : AppCompatActivity(), ListAdapterOnItemClickHandler {
    private lateinit var viewModel: ListActivityViewModel
    private val adapter = ListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initRecycler()
        initViewModel()
    }

    private fun initRecycler() {
        recyclerRepositories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerRepositories.addItemDecoration(ListDividerDecoration(this))
        recyclerRepositories.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ListActivityViewModel::class.java)

        with(viewModel) {

            loading.observe(this@ListActivity, Observer {
                configLoading(it)
            })

            repositories.observe(this@ListActivity,
                    Observer { list -> adapter.setList(list) })

            error.observe(this@ListActivity, Observer {
                configLoading(false)
                showSnackError()
            })
        }

    }

    private fun configLoading(show: Boolean?) = if (show == true) {
        loading.visibility = View.VISIBLE
    } else {
        loading.visibility = View.GONE
    }

    private fun showSnackError() {
        val snackBar = Snackbar.make(frameListActivity, R.string.error, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(R.string.try_again, {
            snackBar.dismiss()

        })
        snackBar.show()
    }

    override fun onItemClick(repositoryFullName: String) {
        val intent = Intent(this, PullRequestActivity::class.java)
        intent.putExtra(EXTRA_REPO_NAME, repositoryFullName)
        startActivity(intent)
    }
}