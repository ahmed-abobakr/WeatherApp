package com.example.weatherapp.base.data

import androidx.lifecycle.LiveData
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherLocalData

open class AppDataManager(open val apiManager: APIManager, val dbManager: DbManager, val preferenceManager: PreferenceManager)
    : DataManager{

    override suspend fun saveWeather(weather: WeatherLocalData) {
        dbManager.saveWeather(weather)
    }

    override suspend fun updateWeather(weather: WeatherLocalData) {
        dbManager.updateWeather(weather)
    }

    override  fun getWeather(): LiveData<List<WeatherLocalData>>{
        return  dbManager.getWeather()
    }

    override suspend fun deleteWeather(weather: WeatherLocalData) {
        dbManager.deleteWeather(weather)
    }

    override fun saveCity(country: String) {
        preferenceManager.saveCity(country)
    }

    override fun getCity(): String {
        return preferenceManager.getCity()
    }

    override fun mapException(throwable: Throwable): Throwable {
        return apiManager.mapException(throwable)
    }

    override fun <T> create(cls: Class<T>): T {
        return apiManager.create(cls)
    }

    override fun <T> execute(apiCall: () -> T): BaseResponseModel<T> {
        return apiManager.execute(apiCall)
    }
}