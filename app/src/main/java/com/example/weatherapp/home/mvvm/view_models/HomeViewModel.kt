package com.example.weatherapp.home.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.base.data.Status
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.base.mvvm.view_models.BaseViewModel
import com.example.weatherapp.getTimeFormatted
import com.example.weatherapp.home.data.HomeDataManager
import com.example.weatherapp.home.data.models.City
import com.example.weatherapp.home.data.models.Weather
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherLocalData
import com.example.weatherapp.mapWeatherDataToWeatherLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception

class HomeViewModel(override val mDataManager: HomeDataManager) : BaseViewModel(mDataManager) {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    private val _savedCity = MutableLiveData<String>()
    val savedCity: LiveData<String>
        get() = _savedCity

    private var _weatherLiveData = mDataManager.getWeather()
    val weatherLiveData: LiveData<List<WeatherLocalData>>
        get() = _weatherLiveData



    fun getSavedCity() {
        val city = mDataManager.getCity()
        _savedCity.value = city
    }

    fun saveCity(city: String) {
        mDataManager.saveCity(city)
        _savedCity.value = mDataManager.getCity()
    }

    fun getWeatherDataSync(refresh: Boolean, city: String) {
        coroutineScope.launch { var weatherData = mDataManager.getWeatherForCity(city)
            try{
                if (!refresh)
                    weatherData.data?.let { saveWeatherData(it.await()) }
                else
                    weatherData.data?.let { updateWeatherData(it.await()) }
            }catch (exception: Exception){
                handleCloudHttpErrors(exception)
            }
        }
    }

    fun saveWeatherData(weather: WeatherData) {
        deleteWeatherData()
        coroutineScope.launch {
            for(element in weather.weather) {
                mDataManager.saveWeather(mapWeatherDataToWeatherLocal(weather.city, element, getTimeFormatted(weather.weather.indexOf(element))))
            }

        }
    }

    fun updateWeatherData(weather: WeatherData) {
        coroutineScope.launch {
            for(element in weather.weather) {
                mDataManager.updateWeather(mapWeatherDataToWeatherLocal(weather.city, element, getTimeFormatted(weather.weather.indexOf(element))))
            }
            _weatherLiveData = mDataManager.getWeather()
        }
    }

    fun deleteWeatherData() {
        coroutineScope.launch {
            val weatherLocal = WeatherLocalData()
            mDataManager.deleteWeather(weatherLocal) }
    }



}