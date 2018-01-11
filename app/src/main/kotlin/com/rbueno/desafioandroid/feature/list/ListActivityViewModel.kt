package com.rbueno.desafioandroid.feature.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.rbueno.desafioandroid.repository.ApiService
import com.rbueno.desafioandroid.repository.GitRepository
import com.rbueno.desafioandroid.repository.GitRepositorySearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivityViewModel : ViewModel() {
    val repositories: LiveData<PagedList<GitRepository>> = LivePagedListBuilder(ListActivityDataSourceFactory(),
            PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(true).build()).build()
}

class ListActivityDataSource : PageKeyedDataSource<Int, GitRepository>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GitRepository>) {
        ApiService.instance.repositoryService().listRepositoryPerPage(1, params.requestedLoadSize).enqueue(object : Callback<GitRepositorySearch> {
            override fun onResponse(call: Call<GitRepositorySearch>?, response: Response<GitRepositorySearch>?) {
                if (response?.code() == 200) {
                    val responseBody = response.body()
                    responseBody?.let { rep -> callback.onResult(rep.items, 0, rep.totalItems, 1, 2) }
                } else {
                    //TRHOW ERROR
                }
            }

            override fun onFailure(call: Call<GitRepositorySearch>?, t: Throwable?) {
                TODO("implementar tratamento de erros") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {
        ApiService.instance.repositoryService().listRepositoryPerPage(params.key, params.requestedLoadSize).enqueue(object : Callback<GitRepositorySearch> {
            override fun onResponse(call: Call<GitRepositorySearch>?, response: Response<GitRepositorySearch>?) {
                val responseBody = response?.body()
                val linkHeader = response?.headers()?.toMultimap()?.get("Link")
                val hasNextPage = linkHeader != null && linkHeader.get(0)?.contains("rel=\"next\"")!!
                responseBody?.let { rep -> callback.onResult(rep.items, if (hasNextPage) params.key + 1 else null) }
            }

            override fun onFailure(call: Call<GitRepositorySearch>?, t: Throwable?) {
                TODO("implementar tratamento de erros") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {}
}

class ListActivityDataSourceFactory : DataSource.Factory<Int, GitRepository> {

    private val sourceLiveData = MutableLiveData<ListActivityDataSource>()

    override fun create(): DataSource<Int, GitRepository> {
        val source = ListActivityDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}