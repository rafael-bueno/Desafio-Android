package com.rbueno.desafioandroid.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.util.Log
import com.rbueno.desafioandroid.repository.GitRepository


class ListActivityViewModel : ViewModel() {
    val repositories: LiveData<PagedList<GitRepository>> = LivePagedListBuilder(ListActivityDataSourceFactory(),
            PagedList.Config.Builder().setPageSize(10).build()).build()
}

class ListActivityDataSource : PageKeyedDataSource<Int, GitRepository>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GitRepository>) {
        Log.d("Load", "initial")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {
        Log.d("Load", "after")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GitRepository>) {
        Log.d("Load", "before")
    }


}

class ListActivityDataSourceFactory : DataSource.Factory<Int, GitRepository> {

    val sourceLiveData = MutableLiveData<ListActivityDataSource>()

    override fun create(): DataSource<Int, GitRepository> {
        val source = ListActivityDataSource()
        sourceLiveData.postValue(source)
        return source
    }

}