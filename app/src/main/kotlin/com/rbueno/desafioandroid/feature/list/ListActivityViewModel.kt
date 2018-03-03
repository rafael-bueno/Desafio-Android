package com.rbueno.desafioandroid.feature.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.rbueno.desafioandroid.api.data.GitRepository
import com.rbueno.desafioandroid.api.data.GitRepositorySearch
import com.rbueno.desafioandroid.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivityViewModel : BaseViewModel() {
    val repositories: LiveData<PagedList<GitRepository>> =
            LivePagedListBuilder(ListActivityDataSourceFactory(error, loading),
                    PagedList.Config.Builder().setPageSize(10).setEnablePlaceholders(true).build()).build()
}

class ListActivityDataSource(private val error: MutableLiveData<Throwable>,
                             private val loading: MutableLiveData<Boolean>)
    : PageKeyedDataSource<Int, GitRepository>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GitRepository>) {
        loading.postValue(true)
        fetchRepository(1, params.requestedLoadSize).enqueue(object : Callback<GitRepositorySearch> {
            override fun onResponse(call: Call<GitRepositorySearch>?, response: Response<GitRepositorySearch>?) {
                loading.postValue(false)
                if (response?.code() == 200) {
                    val responseBody = response.body()
                    responseBody?.let { rep -> callback.onResult(rep.items, 0, rep.totalItems, 1, 2) }
                } else {
                    error.postValue(Throwable(response?.errorBody()?.string()))
                }
            }

            override fun onFailure(call: Call<GitRepositorySearch>?, t: Throwable?) {
                error.postValue(t)
            }
        })
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {

        fetchRepository(params.key, params.requestedLoadSize).enqueue(object : Callback<GitRepositorySearch> {
            override fun onResponse(call: Call<GitRepositorySearch>?, response: Response<GitRepositorySearch>?) {
                if (response?.code() == 200) {
                    val responseBody = response.body()
                    val linkHeader = response.headers()?.toMultimap()?.get("Link")
                    val hasNextPage = linkHeader != null && linkHeader[0]?.contains("rel=\"next\"")!!
                    responseBody?.let { rep -> callback.onResult(rep.items, if (hasNextPage) params.key + 1 else null) }
                } else {
                    error.postValue(Throwable(response?.errorBody()?.string()))
                    //TODO: passando empty list para evitar que a lista fique repetindo itens em caso de erro
                    //estou avaliando o porque desse comportamento
                    callback.onResult(listOf<GitRepository>(), null)
                }

            }

            override fun onFailure(call: Call<GitRepositorySearch>?, t: Throwable?) {
                error.postValue(t)
                callback.onResult(listOf<GitRepository>(), null)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {}
}

class ListActivityDataSourceFactory(private val error: MutableLiveData<Throwable>,
                                    private val loading: MutableLiveData<Boolean>)
    : DataSource.Factory<Int, GitRepository> {

    val sourceLiveData = MutableLiveData<ListActivityDataSource>()

    override fun create(): DataSource<Int, GitRepository> {
        val source = ListActivityDataSource(error, loading)
        sourceLiveData.postValue(source)
        return source
    }
}