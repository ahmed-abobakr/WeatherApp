package com.example.weatherapp.home.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.base.data.DataManager
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.base.mvvm.view_models.BaseViewModel
import com.example.weatherapp.home.data.HomeDataManager
import com.example.weatherapp.home.data.cloud.HomeService
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherSynced
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(override val mDataManager: HomeDataManager) : BaseViewModel(mDataManager) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _savedCity = MutableLiveData<String>()
    val savedCity: LiveData<String>
        get() = _savedCity

    private val _weatherLiveData = MutableLiveData<BaseResponseModel<WeatherData>>()
    val weatherLiveData: LiveData<BaseResponseModel<WeatherData>>
        get() = _weatherLiveData



    fun getSavedCity() {
        val city = mDataManager.getCity()
        _savedCity.value = city
        if(city.isNotEmpty())
            getWeatherDataSync(false, city)
    }

    fun saveCity(city: String) {
        mDataManager.saveCity(city)
        _savedCity.value = mDataManager.getCity()
    }

    fun getWeatherDataSync(refresh: Boolean, city: String) {
        coroutineScope.launch { var weatherData = mDataManager.getWeatherForCity(city)
            _weatherLiveData.value = weatherData
            if(!refresh)
                weatherData.data?.let { saveWeatherData(it) }
            else
                weatherData.data?.let { updateWeatherData(it) }
        }
    }

    fun saveWeatherData(weather: WeatherData) {
        coroutineScope.launch {
            mDataManager.saveWeather(weather)
        }
    }

    fun updateWeatherData(weather: WeatherData) {
        coroutineScope.launch {
            mDataManager.updateWeather(weather)
        }
    }

    fun deleteWeatherData(weather: WeatherData) {
        coroutineScope.launch { mDataManager.deleteWeather(weather) }
    }


}