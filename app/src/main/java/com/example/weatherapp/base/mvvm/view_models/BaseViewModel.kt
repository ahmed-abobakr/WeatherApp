package com.example.weatherapp.base.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.base.data.DataManager

open class BaseViewModel(val mDataManager: DataManager): ViewModel() {


    protected val _mIsLoading = MutableLiveData<Boolean>()
    val mIsLoading: LiveData<Boolean>
        get() = _mIsLoading

    protected val _mCloudException = MutableLiveData<Exception>()
    val mCloudException: LiveData<Exception>
        get() = _mCloudException

    public fun isLoading(loading: Boolean){
        _mIsLoading.value = loading
    }

}