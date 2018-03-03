package com.rbueno.desafioandroid.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    val error = MutableLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()
}