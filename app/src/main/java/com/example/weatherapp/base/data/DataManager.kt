package com.example.weatherapp.base.data

public interface DbManager{

    fun saveWeather()

    fun updateWeather()

    fun getWeather()

    fun deleteWeather()
}

public interface PreferenceManager{

    fun saveCountry(country: String)

    fun getCountry(): String
}

public interface DataManager: DbManager,
    PreferenceManager,
    APIManager