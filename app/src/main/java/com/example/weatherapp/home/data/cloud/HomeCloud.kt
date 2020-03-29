package com.example.weatherapp.home.data.cloud

import com.example.weatherapp.base.data.CloudManager
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.home.data.models.WeatherData
import kotlinx.coroutines.Deferred

class HomeCloud: CloudManager(), HomeServiceImpl  {

    val APPID = "ff7b049f05c50c84761e33976a7792d6"

    override fun getWeatherForCity(city: String): BaseResponseModel<Deferred<WeatherData>> {
         return execute { create(HomeService::class.java).getWeatherForCity(city, APPID, "metric") }
    }


}