package com.example.weatherapp.home.data

import com.example.weatherapp.base.data.*
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.home.data.cloud.HomeServiceImpl
import com.example.weatherapp.home.data.models.WeatherData
import kotlinx.coroutines.Deferred

class HomeDataManager(override val apiManager: HomeServiceImpl,
                      dbManager: DbManager,
                      preferenceManager: PreferenceManager): AppDataManager(apiManager, dbManager, preferenceManager), HomeServiceImpl {
    override fun getWeatherForCity(city: String): BaseResponseModel<Deferred<WeatherData>> {
        return apiManager.getWeatherForCity(city)
    }
}