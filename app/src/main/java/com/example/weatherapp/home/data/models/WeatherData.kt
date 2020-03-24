package com.example.weatherapp.home.data.models

import com.example.weatherapp.base.data.models.BaseResponseModel
import com.squareup.moshi.Json


data class Coord(val lon: Double, val lat: Double)
data class City(val id: Long, val name: String, val country: String, @Json(name = "coord") val coord: Coord)

data class Temp(val min: Int, val max: Int, val eve: Int, val night: Int, val day: Int, val morn: Int)
data class Details(val description: String, val main: String, val id: String)
data class Weather(val dt: Long, val sunrise: Long, @Json(name = "temp") val temp: Temp, val sunset: Long,
                   val deg: Long, @Json(name = "weather") val details: Details, val humidity: Long,
                   val pressure: Long, val clouds: Int, val speed: Int)

data class WeatherData(@Json(name = "city") val city: City, val cnt: Int, val cod: String, val message: Double,
                       @Json(name = "list") val weather: Weather)