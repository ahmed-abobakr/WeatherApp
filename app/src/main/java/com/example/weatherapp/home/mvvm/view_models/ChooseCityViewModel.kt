package com.example.weatherapp.home.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.base.data.PreferenceManager
import com.example.weatherapp.base.mvvm.view_models.BaseViewModel

class ChooseCityViewModel(val preferenceManager: PreferenceManager): ViewModel(){

    private val _cityLiveData = MutableLiveData<String>()
    val cityLiveData: LiveData<String>
        get() = _cityLiveData

    var city: String = preferenceManager.getCity()

    fun saveCity() {
        preferenceManager.saveCity(city)
        _cityLiveData.value = preferenceManager.getCity()
    }

}