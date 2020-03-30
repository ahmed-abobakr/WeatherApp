package com.example.weatherapp.base.data

import androidx.lifecycle.LiveData
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherLocalData

interface DbManager{

    suspend fun saveWeather(weather: WeatherLocalData)

    suspend fun updateWeather(weather: WeatherLocalData)

    fun getWeather(): LiveData<List<WeatherLocalData>>

    suspend fun deleteWeather(weather: WeatherLocalData)
}

interface PreferenceManager{

    fun saveCity(country: String)

    fun getCity(): String
}

interface DataManager: DbManager,
    PreferenceManager,
    APIManager