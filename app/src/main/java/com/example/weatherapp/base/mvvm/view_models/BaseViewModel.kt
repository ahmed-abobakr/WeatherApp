package com.example.weatherapp.base.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    private val _mIsLoading = MutableLiveData<Boolean>()
    val mIsLoading: LiveData<Boolean>
        get() = _mIsLoading

    public fun isLoading(loading: Boolean){
        _mIsLoading.value = loading
    }

}