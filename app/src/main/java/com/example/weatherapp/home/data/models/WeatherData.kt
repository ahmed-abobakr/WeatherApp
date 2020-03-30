package com.example.weatherapp.home.data.models

import com.example.weatherapp.base.data.models.BaseResponseModel
import com.squareup.moshi.Json


data class Coord(val lon: Double, val lat: Double)
data class City(val id: Long, val name: String, val country: String, @Json(name = "coord") val coord: Coord)

data class Temp(val min: Double, val max: Double, val eve: Double, val night: Double, val day: Double, val morn: Double)
data class Details(val description: String, val main: String, val id: Int)
data class Weather(val dt: Long, val sunrise: Long, @Json(name = "temp") val temp: Temp, val sunset: Long,
                   val deg: Long, @Json(name = "weather") val details: List<Details>, val humidity: Long,
                   val pressure: Long, val clouds: Int, val speed: Double)

data class WeatherData(@Json(name = "city") val city: City, val cnt: Int, val cod: String, val message: Double,
                       @Json(name = "list") val weather: List<Weather>)