package com.example.weatherapp.home.data.cloud

import com.example.weatherapp.base.data.APIManager
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.home.data.models.WeatherData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query as Query

interface HomeService {

    @GET("daily")
    fun getWeatherForCity(@Query("q") city: String, @Query("appid") appID: String, @Query("units") units: String): Deferred<WeatherData>
}

interface HomeServiceImpl: APIManager{
    fun getWeatherForCity(city: String): BaseResponseModel<Deferred<WeatherData>>
}

