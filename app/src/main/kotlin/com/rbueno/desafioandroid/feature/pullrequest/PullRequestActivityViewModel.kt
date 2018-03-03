package com.rbueno.desafioandroid.feature.pullrequest

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.rbueno.desafioandroid.api.data.GitRepositoryPullRequest
import com.rbueno.desafioandroid.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullRequestActivityViewModel(repoName: String) : BaseViewModel() {

    val pullRequests: LiveData<PagedList<GitRepositoryPullRequest>> =
            LivePagedListBuilder(PullRequestDataSourceFactory(repoName, error, loading),
                    PagedList.Config.Builder().setPageSize(10).setInitialLoadSizeHint(10).build()).build()
}

class PullRequestDataSource(private val repoName: String,
                            private val error: MutableLiveData<Throwable>,
                            private val loading: MutableLiveData<Boolean>) :
        PageKeyedDataSource<Int, GitRepositoryPullRequest>() {

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepositoryPullRequest>) {
        fetchLisPullRequest(repoName, params.key, params.requestedLoadSize)
                .enqueue(object : Callback<List<GitRepositoryPullRequest>> {
                    override fun onResponse(call: Call<List<GitRepositoryPullRequest>>?, response: Response<List<GitRepositoryPullRequest>>?) {
                        val responseBody = response?.body()
                        val hasNextPage = response?.headers()?.toMultimap()?.get("Link")?.get(0)?.contains("rel=\"next\"")
                        responseBody?.let { rep -> callback.onResult(rep, if (hasNextPage!!) params.key + 1 else null) }
                    }

                    override fun onFailure(call: Call<List<GitRepositoryPullRequest>>?, t: Throwable?) {
                        error.postValue(t)
                    }
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepositoryPullRequest>) {}

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GitRepositoryPullRequest>) {
        loading.postValue(true)
        fetchLisPullRequest(repoName, 1, params.requestedLoadSize)
                .enqueue(object : Callback<List<GitRepositoryPullRequest>> {
                    override fun onResponse(call: Call<List<GitRepositoryPullRequest>>?, response: Response<List<GitRepositoryPullRequest>>?) {
                        loading.postValue(false)
                        val responseBody = response?.body()
                        val linkHeader = response?.headers()?.toMultimap()?.get("Link")
                        val hasNextPage = linkHeader != null && linkHeader.get(0)?.contains("rel=\"next\"")!!
                        responseBody?.let { rep -> callback.onResult(rep, 1, if (hasNextPage) 2 else null) }
                    }

                    override fun onFailure(call: Call<List<GitRepositoryPullRequest>>?, t: Throwable?) {
                        error.postValue(t)
                    }
                })
    }
}

class PullRequestDataSourceFactory(private val repoName: String,
                                   private val error: MutableLiveData<Throwable>,
                                   private val loading: MutableLiveData<Boolean>) :
        DataSource.Factory<Int, GitRepositoryPullRequest> {

    private val sourceLiveData = MutableLiveData<PullRequestDataSource>()

    override fun create(): DataSource<Int, GitRepositoryPullRequest> {
        val source = PullRequestDataSource(repoName, error, loading)
        sourceLiveData.postValue(source)
        return source
    }
}

class PullRequestActivityViewModelFactory(private val repoName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = PullRequestActivityViewModel(repoName) as T
}