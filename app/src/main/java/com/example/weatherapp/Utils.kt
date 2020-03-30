package com.example.weatherapp

import com.example.weatherapp.home.data.models.City
import com.example.weatherapp.home.data.models.Weather
import com.example.weatherapp.home.data.models.WeatherLocalData
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


const val DEFAULT_CITY = "Cairo"

fun getTimeFormatted(addedDays: Int): String{
    return try {
        val sdf = SimpleDateFormat("dd-MM-YYYY")
        val timeNow = Calendar.getInstance()
        timeNow.add(Calendar.DAY_OF_MONTH, addedDays)
        sdf.format(timeNow.time)
    }catch (exception: Exception){
        ""
    }
}

fun mapWeatherDataToWeatherLocal(city: City, weather: Weather, dateStr: String): WeatherLocalData {
    val weatherLocal = WeatherLocalData()
    weatherLocal.locationID = city.id
    weatherLocal.cityName = city.name
    weatherLocal.date = dateStr
    weatherLocal.humidity = weather.humidity
    weatherLocal.pressure = weather.pressure
    weatherLocal.windSpeed = weather.speed.toInt()
    weatherLocal.windDirection = weather.deg
    weatherLocal.low = weather.temp.min
    weatherLocal.high = weather.temp.max
    weatherLocal.description = weather.details[0].description
    weatherLocal.descriptionID = weather.details[0].id
    return weatherLocal
}