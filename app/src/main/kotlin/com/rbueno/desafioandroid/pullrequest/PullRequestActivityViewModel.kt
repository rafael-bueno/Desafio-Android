package com.rbueno.desafioandroid.pullrequest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.rbueno.desafioandroid.repository.ApiService
import com.rbueno.desafioandroid.repository.GitRepositoryPullRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullRequestActivityViewModel : ViewModel() {

    lateinit var repositoryName: String

    val pullRequests: LiveData<PagedList<GitRepositoryPullRequest>> = LivePagedListBuilder(PullRequestDataSourceFactory(),
            PagedList.Config.Builder().setPageSize(10).setInitialLoadSizeHint(10).build()).build()

}


class PullRequestDataSource : PageKeyedDataSource<Int, GitRepositoryPullRequest>() {

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepositoryPullRequest>) {
        ApiService.instance.repositoryService().listPullRequestPerRepo("iluwatar/java-design-patterns", params.key, params.requestedLoadSize)
                .enqueue(object : Callback<List<GitRepositoryPullRequest>> {
                    override fun onResponse(call: Call<List<GitRepositoryPullRequest>>?, response: Response<List<GitRepositoryPullRequest>>?) {
                        val responseBody = response?.body()
                        val hasNextPage = response?.headers()?.toMultimap()?.get("Link")?.get(0)?.contains("rel=\"next\"")
                        responseBody?.let { rep -> callback.onResult(rep, if (hasNextPage!!) params.key + 1 else null) }
                    }

                    override fun onFailure(call: Call<List<GitRepositoryPullRequest>>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepositoryPullRequest>) {}

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GitRepositoryPullRequest>) {

        ApiService.instance.repositoryService().listPullRequestPerRepo("iluwatar/java-design-patterns", 1, params.requestedLoadSize)
                .enqueue(object : Callback<List<GitRepositoryPullRequest>> {
                    override fun onResponse(call: Call<List<GitRepositoryPullRequest>>?, response: Response<List<GitRepositoryPullRequest>>?) {
                        val responseBody = response?.body()
                        val linkHeader = response?.headers()?.toMultimap()?.get("Link")
                        val hasNextPage = linkHeader != null && linkHeader.get(0)?.contains("rel=\"next\"")!!
                        responseBody?.let { rep -> callback.onResult(rep, 1, if (hasNextPage) 2 else null) }
                    }

                    override fun onFailure(call: Call<List<GitRepositoryPullRequest>>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }
}

class PullRequestDataSourceFactory : DataSource.Factory<Int, GitRepositoryPullRequest> {
    private val sourceLiveData = MutableLiveData<PullRequestDataSource>()

    override fun create(): DataSource<Int, GitRepositoryPullRequest> {
        val source = PullRequestDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}