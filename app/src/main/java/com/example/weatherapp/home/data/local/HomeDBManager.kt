package com.example.weatherapp.home.data.local

import androidx.lifecycle.LiveData
import com.example.weatherapp.base.data.DbManager
import com.example.weatherapp.base.data.WeatherDatabaseDao
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherLocalData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeDBManager(val database: WeatherDatabaseDao): DbManager {

    override suspend fun saveWeather(weather: WeatherLocalData) {
        withContext(Dispatchers.IO){
            database.insert(weather)
        }
    }

    override suspend fun updateWeather(weather: WeatherLocalData) {
        withContext(Dispatchers.IO){
            database.update(weather)
        }
    }

    override fun getWeather(): LiveData<List<WeatherLocalData>> {
        return database.getAllWeatherData()
    }

    override suspend fun deleteWeather(weather: WeatherLocalData) {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }
}